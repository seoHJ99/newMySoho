package com.study.springboot.client.service;

import com.study.springboot.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailSaveDto {
    private int ordersIDX;
    private int item_IDX;
    private int odetail_QTY;
    private String odetail_STATUS;

    public OrderDetail toSaveEntity(){
        return OrderDetail.builder()
                .item_IDX(item_IDX)
                .ordersIDX(ordersIDX)
                .odetail_QTY(odetail_QTY)
                .odetail_STATUS(odetail_STATUS)
                .build();
    }
}
