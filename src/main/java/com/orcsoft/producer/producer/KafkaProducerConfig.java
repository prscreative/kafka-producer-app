package com.orcsoft.producer.producer;

import java.io.IOException;
import java.util.Map;

import com.orcsoft.producer.config.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

@Configuration
public class KafkaProducerConfig {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerConfig.class);

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        logger.info("KafkaProducerConfig > producerFactory start...");
        Map<String, Object> configProps = configProperties.getKafkaPropertiesConfig();
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() throws IOException {
        return new KafkaTemplate<>(producerFactory());
    }

}
