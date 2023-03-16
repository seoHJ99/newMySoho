package com.study.springboot.client.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class CartInformation {
    private String item_NAME;
    private String item_IMAGE;
    private int item_COUNT;
    private int item_PRICE;
    private int item_DISCOUNT;
    private String item_OPTION;
    private int item_PRICE_DISCOUNT;
    private int item_IDX;

    @Builder
    public CartInformation(String item_NAME, String item_IMAGE, int item_COUNT, int item_PRICE, int item_DISCOUNT, int item_PRICE_DISCOUNT, int item_IDX, String item_OPTION) {
        this.item_NAME = item_NAME;
        this.item_PRICE_DISCOUNT = item_PRICE_DISCOUNT;
        this.item_IDX = item_IDX;
        this.item_IMAGE = item_IMAGE;
        this.item_COUNT = item_COUNT;
        this.item_PRICE = item_PRICE;
        this.item_DISCOUNT = item_DISCOUNT;
        this.item_OPTION = item_OPTION;
    }
}
