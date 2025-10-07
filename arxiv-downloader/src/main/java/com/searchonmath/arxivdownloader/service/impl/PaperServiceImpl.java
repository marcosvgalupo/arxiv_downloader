package com.searchonmath.arxivdownloader.service.impl;


import com.searchonmath.arxivdownloader.config.application.ExecutionProperties;
import com.searchonmath.arxivdownloader.database.repository.PaperRepository;
import com.searchonmath.arxivdownloader.dto.ThreadPaperBlockInfoDTO;
import com.searchonmath.arxivdownloader.service.ArxivService;
import com.searchonmath.arxivdownloader.service.PaperService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("paperService")
@Log4j2
public class PaperServiceImpl implements PaperService {
    private final ExecutionProperties properties;
    private final ArxivService arxivService;
    private final PaperRepository repository;

    @Autowired
    public PaperServiceImpl(ExecutionProperties properties, ArxivService arxivService, PaperRepository repository) {
        this.properties = properties;
        this.arxivService = arxivService;
        this.repository = repository;
    }

    @Override
    public void downloadFromArxivAndProcess() {
        Integer registerNumber = repository.findMaxIndex();
        int blockSize = (int) Math.ceil(registerNumber/(double)properties.getThreadNumber());
        int lastStartIndex = 1;
        int lastEndIndex =  lastStartIndex + blockSize;
        for(int i = 0;i < properties.getThreadNumber(); i++){
            arxivService.run(ThreadPaperBlockInfoDTO.builder()
                    .sleep(properties.getSleep())
                    .chunkSize(properties.getChunkSize())
                    .downloadResult(properties.getDownloadResult())
                    .startIndex(lastStartIndex)
                    .endIndex(lastEndIndex)
                    .build());
            lastStartIndex += blockSize;
            lastEndIndex += blockSize;
        }
    }

}
