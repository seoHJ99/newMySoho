package com.study.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "orders_detail")
@Getter
@AllArgsConstructor
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
}
