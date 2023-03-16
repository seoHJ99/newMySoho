package com.study.springboot.admin.dto;

import com.study.springboot.entity.OrderDetail;
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
    private String odetail_STATUS;

    public OrderDetailDto(OrderDetail entity) {
        this.odetail_IDX = entity.getOdetail_IDX();
        this.orders_IDX = entity.getOrdersIDX();
        this.item_IDX = entity.getItem_IDX();
        this.odetail_QTY = entity.getOdetail_QTY();
        this.odetail_STATUS = entity.getOdetail_STATUS();
    }

    public OrderDetail toModifyEntity(){
        return OrderDetail.builder()
                .odetail_STATUS(odetail_STATUS)
                .ordersIDX(orders_IDX)
                .odetail_QTY(odetail_QTY)
                .item_IDX(item_IDX)
                .odetail_IDX(odetail_IDX)
                .build();
    }
}
