package com.searchonmath.arxivdownloader.service;

import com.searchonmath.arxivdownloader.database.entity.Paper;
import com.searchonmath.arxivdownloader.dto.ThreadPaperBlockInfoDTO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public interface ArxivService {

    String download(Paper paper);

    void process(String file);

    @Async("arxivThreadPool")
    @Transactional
    void run(ThreadPaperBlockInfoDTO dataBlockInfo);
}
