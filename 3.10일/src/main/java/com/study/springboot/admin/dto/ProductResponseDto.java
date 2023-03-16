package com.study.springboot.admin.dto;



import com.study.springboot.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private Integer item_idx;
    private String item_CATEGORY1;
    private String item_CATEGORY2;
    private String item_NAME;
    private int item_PRICE;
    private int item_DISCOUNT;
    private int item_PRICE_DISCOUNT;
    private int item_ORIGINAL;
    private String item_OPTION;
    private int item_AMOUNT;
    private String item_IMAGE;
    private String item_DETAIL;
    private LocalDate registeredDate;
    private int item_SELL;


    public ProductResponseDto(Product entity) {
        this.item_idx = entity.getItem_idx();
        this.item_CATEGORY1 = entity.getItem_CATEGORY1();
        this.item_CATEGORY2 = entity.getItem_CATEGORY2();
        this.item_NAME = entity.getItem_NAME();
        this.item_PRICE = entity.getItem_PRICE();
        this.item_DISCOUNT = entity.getItem_DISCOUNT();
        this.item_OPTION = entity.getItem_OPTION();
        this.item_ORIGINAL = entity.getItemORIGINAL();
        this.item_AMOUNT = entity.getItem_AMOUNT();
        this.item_IMAGE = entity.getItem_IMAGE();
        this.item_DETAIL = entity.getItem_DETAIL();
        this.item_SELL = entity.getItem_SELL();
        this.registeredDate = entity.getRegisteredDate();
    }
}
