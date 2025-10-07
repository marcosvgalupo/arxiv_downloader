package com.searchonmath.arxivreader.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchonmath.arxivglobal.dto.ArxivPage;
import com.searchonmath.arxivglobal.queue.Producer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Log4j2
@Service
public class ReaderService {
    private final Producer producer;
    private final ObjectMapper mapper =  new ObjectMapper();

    @Autowired
    public ReaderService(Producer producer) {
        this.producer = producer;
    }

    @Async("readerPool")
    public void readFile(String folder, String filename) {
        try{
            log.info("Reading file {} from folder {}", filename, folder);
            String id = filename.replace("_BARRA_", "/").replace("_BARRAINVERTIDA_", "\\").replace(".html", "");
            String path = String.format("%s/%s", folder, filename);

            log.trace("Building message for {}", filename);
            ArxivPage page = ArxivPage.builder()
                    .id(id)
                    .path(path)
                    .build();

            producer.send(mapper.writeValueAsString(page));
        }catch (JsonProcessingException ex) {
            log.error("Error when serialize page to post on queue. \n Exception: {}", ex.getMessage());
        }

    }
}
