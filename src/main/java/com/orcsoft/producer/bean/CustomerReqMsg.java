package com.orcsoft.producer.bean;

import lombok.Data;

import java.util.List;

@Data
public class CustomerReqMsg {

    private List<Customer> customerList;

}
