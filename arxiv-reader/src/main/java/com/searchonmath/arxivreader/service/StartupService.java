package com.searchonmath.arxivreader.service;

import com.searchonmath.arxivreader.config.FolderList;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class StartupService {
    private final FolderList folders;
    private final FolderService folderService;

    @Autowired
    public StartupService(FolderList folders, FolderService folderService) {
        this.folders = folders;
        this.folderService = folderService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startUp() {
        log.info("Starting scan folders");
        folders.getList().forEach(folderService::scanFolder);
    }

}
