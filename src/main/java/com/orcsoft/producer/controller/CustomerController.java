package com.orcsoft.producer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.orcsoft.producer.bean.Customer;
import com.orcsoft.producer.bean.CustomerReqMsg;
import com.orcsoft.producer.service.KafkaProducer;

@RestController
@RequestMapping(value = "/kafka")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @PostMapping(value = "publishMessage", consumes = "application/json", produces = "application/json")
    public String publishMessage(@RequestBody CustomerReqMsg request) throws Exception {
        String respMessage = "Send Message Error!";
        try {
            logger.info("Start Send Message Kafka, bulkPaymentFeeMessageBean : {}", request.getCustomerList().size());
            /* Produce message to Kafka */
            for(Customer customer : request.getCustomerList()) {
                String customerJsonStr = new Gson().toJson(customer);
                logger.info("Customer Json : {}", customerJsonStr);
                kafkaProducer.sendMessage(customerJsonStr);
            }

            respMessage = "Send Message Success";
        } catch (Exception e) {
            logger.error("Send Message Error!, Exception : {}", e.getMessage(), e);
            throw e;
        }
        return respMessage;
    }
}
