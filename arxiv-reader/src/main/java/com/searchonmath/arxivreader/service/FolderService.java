package com.searchonmath.arxivreader.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
@Log4j2
@Service
public class FolderService {

    private final ReaderService readerService;

    @Autowired
    public FolderService(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Async("folderPool")
    public void scanFolder(String folder){
        log.info("Scanning folder {}", folder);
        File[] files = new File(folder).listFiles();
        assert files != null;
        log.info("{} files to read in folder {}", files.length, folder);
        Arrays.asList(files).forEach(i -> readerService.readFile(folder, i.getName()));
        log.info("All files have been read");
    }
}
