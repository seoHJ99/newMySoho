package com.study.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_IDX")
    private int ordersIDX;
    private Integer member_IDX;
    private Integer coupon_IDX;
    private int nonmember_IDX =0;
    @Column(name = "orders_DATE")
    private LocalDate ordersDATE;
    private int accumulate_MILEAGE;
    private int using_Mileage;
    private String orders_NAME;
    private String orders_PHONE;
    private String senders_NAME;
    private String senders_PHONE;
    private String orders_ADDRESS;
    private String orders_POST;
    private int orders_AMOUNT;
    private String orders_PAYMENT;
    private String orders_STATUS;
    private String orders_DELIVERYPAY;
    private String orders_DELIVERY_PS = "default";
}
