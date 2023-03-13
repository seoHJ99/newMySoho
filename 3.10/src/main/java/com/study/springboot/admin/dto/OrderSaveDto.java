package com.study.springboot.admin.dto;

import com.study.springboot.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaveDto {
    private int member_IDX; // 회원번호
    private int nonmember_IDX; // 비회원 번호
    private int coupon_IDX; // 쿠폰 번호. 쿠폰 적용시
    private int orders_AMOUNT; // 총 구매 금액
    private int accumulate_MILEAGE; // 현재 주문으로 쌓인 적립금
    private int using_MILEAGE; // 주문에 사용한 적립금
    private String orders_DELIVERYPAY; // 배송비
    private LocalDate orders_DATE = LocalDate.now(); // 주문 날짜
    private String orders_NAME; // 주문자 이름
    private String orders_PHONE; // 전번
    private String senders_NAME; // 주문자 이름
    private String senders_PHONE; // 전번

    private String orders_ADDRESS; // 주소
    private String orders_POST; // 우편번호
    private String orders_PAYMENT; // 결제 방식
    private String orders_STATUS; // 주문 상태

    private String orders_DELIVERY_PS; // 배달 요청사항

    public Order toSaveEntity(){
     return Order.builder()
             .ordersDATE(orders_DATE).orders_NAME(orders_NAME).orders_AMOUNT(orders_AMOUNT).accumulate_MILEAGE(accumulate_MILEAGE).using_Mileage(using_MILEAGE)
             .orders_PAYMENT(orders_PAYMENT).orders_PHONE(orders_PHONE).member_IDX(member_IDX).nonmember_IDX(nonmember_IDX).coupon_IDX(coupon_IDX)
             .orders_DELIVERYPAY(orders_DELIVERYPAY).orders_PHONE(orders_PHONE).orders_POST(orders_POST).orders_ADDRESS(orders_ADDRESS)
             .orders_STATUS(orders_STATUS).orders_DELIVERY_PS(orders_DELIVERY_PS).senders_NAME(senders_NAME).senders_PHONE(senders_PHONE)
             .build();
    }
}
