package com.searchonmath.arxivdownloader.service.impl;

import com.searchonmath.arxivdownloader.client.ArxivFeignClient;
import com.searchonmath.arxivdownloader.database.entity.Paper;
import com.searchonmath.arxivdownloader.database.repository.PaperRepository;
import com.searchonmath.arxivdownloader.dto.ThreadPaperBlockInfoDTO;
import com.searchonmath.arxivdownloader.enumerator.DownloadResult;
import com.searchonmath.arxivdownloader.service.ArxivService;
import com.searchonmath.arxivdownloader.util.FeignUtil;
import com.searchonmath.arxivdownloader.util.FileHandler;
import feign.FeignException;
import feign.Response;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service("arxivService")
@Log4j2
public class ArxivServiceImpl implements ArxivService {
    private final ArxivFeignClient client;
    private final PaperRepository paperRepository;
    private final FileHandler fileHandler;

    @Autowired
    public ArxivServiceImpl(ArxivFeignClient client, PaperRepository paperRepository, FileHandler fileHandler) {
        this.client = client;
        this.paperRepository = paperRepository;
        this.fileHandler = fileHandler;
    }

    @Override
    public String download(Paper paper) {
        try {
            Response response = client.getPage(paper.getId());
            return IOUtils.toString(response.body().asInputStream(), "utf-8");
        } catch (IOException e) {
            log.warn("Error when get body as String from arxiv paper {}", paper.getId());
        } catch (FeignException e) {
            FeignUtil.feignExceptionHandler(e);
        }
        return null;
    }

    @Override
    public void process(String file) {
        //TODO pre processing code here
    }

    @Async("arxivThreadPool")
    @Override
    public void run(ThreadPaperBlockInfoDTO dataBlockInfo) {
        while (true) {
            try {
                List<Paper> papers = paperRepository.findPapersForDownload(dataBlockInfo.getStartIndex(), dataBlockInfo.getEndIndex(), dataBlockInfo.getDownloadResult(), dataBlockInfo.getChunkSize());
                if (papers.isEmpty())
                    return;
                for (Paper paper : papers) {
                    try {
                        String file = download(paper);
                        if (StringUtils.isNotBlank(file) && file.contains("ltx_Math")) {
                            fileHandler.writeFile(paper.getId(), file);
                            paper.setDownloadResult(DownloadResult.SUCCESSFUL.getValue());
                            process(file);
                        } else {
                            log.warn("Paper with id {} wasn't downloaded", paper.getId());
                            paper.setDownloadResult(DownloadResult.NOT_DOWNLOADED.getValue());
                        }
                    } catch (IOException e) {
                        log.error("Error when try write a file with name {}", paper.getId());
                        paper.setDownloadResult(DownloadResult.ERROR.getValue());
                    }
                    paperRepository.save(paper);

                    Thread.sleep(dataBlockInfo.getSleep());
                }
            } catch (InterruptedException e) {
                log.error("Error on Thread sleep");
            }
        }
    }
}
