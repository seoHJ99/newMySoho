package com.study.springboot.admin.dto;

import com.study.springboot.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CouponResoponseDTO {

    private String coupon_NAME ;
    private int coupon_DISCOUNT;
    private int coupon_PRICE;
    private LocalDate coupon_REGDATE;
    private int coupon_ENDDATE;
    private String coupon_USED = "미사용";
    private int memberIDX;
    private int item_IDX;

    public CouponResoponseDTO(Coupon entity) {
        this.coupon_NAME = entity.getCoupon_NAME();
        if(entity.getCoupon_DISCOUNT()>0) {
            this.coupon_DISCOUNT = entity.getCoupon_DISCOUNT();
        }else {
            this.coupon_PRICE = entity.getCoupon_PRICE();
        }
        this.coupon_REGDATE = entity.getCoupon_REGDATE();
        this.coupon_ENDDATE = entity.getCoupon_ENDDATE();
        this.coupon_USED = entity.getCoupon_USED();
        this.memberIDX = entity.getMemberIDX();
        this.item_IDX = entity.getItem_IDX();
    }
}
