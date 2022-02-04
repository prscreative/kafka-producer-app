package com.orcsoft.producer.service;

import com.orcsoft.producer.config.ConfigProperties;
import com.orcsoft.producer.kafka.KafkaProducerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ConfigProperties configProperties;

    public void sendMessage(String jsonData) {
        logger.info("Start Kafka producer publish message process...");
        String groupId = configProperties.getGroupIdConfig();
        
        kafkaTemplate.send(groupId, jsonData);
        
        logger.info("End Kafka producer publish message process...");
    }
}
