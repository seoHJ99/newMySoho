package com.study.springboot.admin.dto;

import com.study.springboot.entity.Order;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
@Setter
@NoArgsConstructor
public class OrderResponseDto {
    private int orders_IDX; // 주문번호
    private int member_IDX ; // 회원번호
    private int nonmember_IDX ; // 비회원 번호
    private int coupon_IDX; // 쿠폰 번호. 쿠폰 적용시
    private int using_MILEAGE; // 사용 마일리지 금액
    private int accumulate_MILEAGE; // 적립 마일리지 금액
    private int orders_AMOUNT; // 총 구매 금액
    private String orders_DELIVERYPAY; // 배송비
    private LocalDate orders_DATE; // 주문 날짜
    private String orders_NAME; // 주문자 이름
    private String orders_PHONE; // 전번

    private String senders_NAME; // 보낸사람
    private String senders_PHONE; // 보낸사람 전화

    private Integer orders_ACCOUNT; // 무통장 입금 계좌
    private Integer orders_RETURN_ACCOUNT;
    private String orders_ADDRESS; // 주소
    private String orders_POST; // 우편번호
    private String orders_PAYMENT; // 결제 방식
    private String orders_STATUS; // 주문 상태
    private String orders_DELIVERY_PS; // 배달 요청사항

    public OrderResponseDto(Order entity){
        this.orders_IDX = entity.getOrdersIDX();
        if(entity.getMember_IDX()!=null) {
            this.member_IDX = entity.getMember_IDX();
            this.using_MILEAGE = entity.getUsing_Mileage();
            this.accumulate_MILEAGE = entity.getAccumulate_MILEAGE();
        }else {
            this.nonmember_IDX = entity.getNonmember_IDX();
        }
        if(entity.getCoupon_IDX() != null) {
            this.coupon_IDX = entity.getCoupon_IDX();
        }
        this.orders_AMOUNT = entity.getOrders_AMOUNT();
        this.orders_DELIVERYPAY = entity.getOrders_DELIVERYPAY();
        this.orders_DATE = entity.getOrdersDATE();
        this.orders_NAME = entity.getOrders_NAME();
        this.orders_PHONE = entity.getOrders_PHONE();
//        if(entity.getOrders_ACCOUNT() !=null) {
//            this.orders_ACCOUNT = entity.getOrders_ACCOUNT();
//        }
//        if(entity.getOrders_RETURN_ACCOUNT() !=null) {
//            this.orders_RETURN_ACCOUNT = entity.getOrders_RETURN_ACCOUNT();
//        }
        this.orders_ADDRESS = entity.getOrders_ADDRESS();
        this.orders_POST = entity.getOrders_POST();
        this.orders_PAYMENT = entity.getOrders_PAYMENT();
        this.orders_STATUS = entity.getOrders_STATUS();
        this.orders_DELIVERY_PS = entity.getOrders_DELIVERY_PS();

        this.senders_NAME = entity.getSenders_NAME();
        this.senders_PHONE = entity.getSenders_PHONE();
    }
}
