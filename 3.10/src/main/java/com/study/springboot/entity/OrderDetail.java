package com.study.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders_detail")
@Getter
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int odetail_IDX;
    @Column(name = "orders_IDX")
    private int ordersIDX;
    private int item_IDX;
    private int odetail_QTY;
    private String odetail_STATUS;

    @Builder
    public OrderDetail(int odetail_IDX, int ordersIDX, int item_IDX, int odetail_QTY, String odetail_STATUS) {
        this.odetail_IDX = odetail_IDX;
        this.ordersIDX = ordersIDX;
        this.item_IDX = item_IDX;
        this.odetail_QTY = odetail_QTY;
        this.odetail_STATUS = odetail_STATUS;
    }
}
