package com.study.springboot.client.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//made by 형민
public class OrdersStatus {

    private int noPayCnt;
    private int orderReadyCnt;
    private int orderingCnt;
    private int completeCnt;

    private String orderSTATUS;

}
