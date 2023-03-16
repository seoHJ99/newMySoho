package com.study.springboot.entity;

import com.study.springboot.admin.dto.CouponResoponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "coupon")
@NoArgsConstructor
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int coupon_IDX;
    private String coupon_NAME ;
    private int coupon_DISCOUNT;
    private int coupon_PRICE;
    private LocalDate coupon_REGDATE;
    private int coupon_ENDDATE;
    private String coupon_USED = "미사용";
    @Column(name = "member_IDX")
    private int memberIDX;
    private int item_IDX;

    public Coupon(CouponResoponseDTO dto) {
        this.coupon_NAME = dto.getCoupon_NAME();
        this.coupon_DISCOUNT = dto.getCoupon_DISCOUNT();
        this.coupon_PRICE = dto.getCoupon_PRICE();
        this.coupon_REGDATE = dto.getCoupon_REGDATE();
        this.coupon_ENDDATE = dto.getCoupon_ENDDATE();
        this.coupon_USED = dto.getCoupon_USED();
        this.memberIDX =dto.getMemberIDX();
        this.item_IDX = dto.getItem_IDX();
    }
}