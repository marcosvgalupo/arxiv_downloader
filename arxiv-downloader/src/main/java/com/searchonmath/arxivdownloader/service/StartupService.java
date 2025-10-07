package com.searchonmath.arxivdownloader.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class StartupService {

    private final PaperService paperService;

    @Autowired
    public StartupService(PaperService paperService) {
        this.paperService = paperService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startUp() {
        log.info("Starting download");
        paperService.downloadFromArxivAndProcess();
    }
}
