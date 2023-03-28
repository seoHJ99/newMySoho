package com.study.springboot.client.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
//made by 형민
public class OrderDetailTemp {

    private List<ProductInfo> productInfoList;

    private int orders_IDX;
    private LocalDate orders_DATE;
    private String orders_NAME;
    private String orders_PHONE;
    private String orders_ADDRESS;
    private String orders_PAYMENT;
    private String senders_NAME;
    private String senders_PHONE;
    private int using_MILEAGE;
    private int member_IDX;
    private String orders_STATUS;
    private int item_PRICE;
    private String orders_DELIVERYPAY;
    private int item_DISCOUNT;
    private int item_LAST_PRICE;
    private int refund_PRICE;
    private int refundCnt;
}
