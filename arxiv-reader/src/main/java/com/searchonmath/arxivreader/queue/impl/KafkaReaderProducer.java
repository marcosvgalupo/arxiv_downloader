package com.searchonmath.arxivreader.queue.impl;

import com.searchonmath.arxivglobal.queue.Producer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Log4j2
@Primary
@Component("kafkaProducer")
public class KafkaReaderProducer implements Producer {

    @Value("${kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaReaderProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String message) {
        log.info("Sending message to {} topic", topic);
        kafkaTemplate.send(topic, message);
    }
}
