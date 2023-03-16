package com.study.springboot.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderInfoDto {
    private String item_name;
    private int item_price;
    private int item_quantity;
    private int item_total;  // 합계 가격
    private String item_status;
}
