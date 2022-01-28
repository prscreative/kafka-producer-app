package com.orcsoft.producer.config;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Data;

import javax.annotation.PostConstruct;

@Data
@Component
public class ConfigProperties {

    private static final Logger logger = LoggerFactory.getLogger(ConfigProperties.class);

    @Value("${bootstrap.servers}")
    private String bootstrapServerConfig;

    @Value("${group.id}")
    private String groupIdConfig;

    @Value("${auto.offset.reset}")
    private String autoOffsetResetConfig;

    @PostConstruct
    public void printConfig() {
        logger.info("ConfigProperties >  bootstrapServerConfi={} , groupIdConfig={}, autoOffsetResetConfig={}, loopNumber={}",
                getBootstrapServerConfig(),
                getGroupIdConfig(),
                getAutoOffsetResetConfig()
        );
    }

    /**
     * @return
     */
    public Map<String, Object> getKafkaPropertiesConfig() {
        Map<String, Object> propertiesConfig = new HashMap<>();
        propertiesConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServerConfig());
        propertiesConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        propertiesConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        propertiesConfig.put(ConsumerConfig.GROUP_ID_CONFIG, getGroupIdConfig());
        propertiesConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, getAutoOffsetResetConfig());
        return propertiesConfig;
    }

    /**
     * @return
     */
    public Properties getKafkaProperties() {
        Map<String, Object> propertiesConfigMap = getKafkaPropertiesConfig();
        Properties properties = new Properties();
        for (Map.Entry<String, Object> propertiesConfig : propertiesConfigMap.entrySet()) {
            properties.put(propertiesConfig.getKey(), propertiesConfig.getValue());
        }
        return properties;
    }

}
