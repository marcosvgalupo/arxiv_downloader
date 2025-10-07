package com.searchonmath.arxivprocessor.queue.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.searchonmath.arxivglobal.dto.ArxivPage;
import com.searchonmath.arxivglobal.queue.Consumer;
import com.searchonmath.arxivprocessor.service.PageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class KafkaProcessorConsumer implements Consumer {
    private final ObjectMapper mapper = new ObjectMapper();
    private final PageService pageProcessor;

    @Autowired
    public KafkaProcessorConsumer(PageService pageProcessor) {
        this.pageProcessor = pageProcessor;
    }

    @KafkaListener(
            groupId = "${kafka.group-id}",
            topicPartitions = {
                    @TopicPartition(topic = "${kafka.topic.name}", partitions = "${kafka.topic.partition}")
            }
    )
    public void listen(String message) {
        try{
            ArxivPage page = mapper.readValue(message, ArxivPage.class);
            pageProcessor.process(page);
        }catch (JsonProcessingException ex){
            log.error("Error when deserialize ArxivPage object from kafka message. \n Exception: {}", ex.getMessage());
        }
    }
}
