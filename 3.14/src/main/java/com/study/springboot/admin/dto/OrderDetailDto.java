package com.study.springboot.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderDetailDto {
    private int odetail_IDX;
    private int orders_IDX;
    private int item_IDX;
    private int odetail_QTY;

}
