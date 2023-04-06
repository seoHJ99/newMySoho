package com.study.springboot.client.dto;


import com.study.springboot.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Comparator;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto implements Comparable<ProductResponseDto> {

    private Integer item_idx;
    private String item_CATEGORY1;
    private String item_CATEGORY2;
    private String item_NAME;
    private int item_PRICE;
    private int item_DISCOUNT;
    private String item_OPTION;
    private int item_PRICE_DISCOUNT;
    private int item_ORIGINAL;
    private int item_AMOUNT;
    private String item_IMAGE;
    private String item_DETAIL;
    private int review_SCORE;
    private int item_SELL;
    private LocalDate registeredDate;
    private int review_COUNT;

    public ProductResponseDto(Product entity) {
        this.item_idx = entity.getItem_idx();
        this.item_CATEGORY1 = entity.getItem_CATEGORY1();
        this.item_CATEGORY2 = entity.getItem_CATEGORY2();
        this.item_NAME = entity.getItem_NAME();
        this.item_PRICE = entity.getItem_PRICE();
        this.item_OPTION = entity.getItem_OPTION();
        this.item_DISCOUNT = entity.getItem_DISCOUNT();
        this.item_ORIGINAL = entity.getItemORIGINAL();
        this.item_AMOUNT = entity.getItem_AMOUNT();
        this.item_IMAGE = entity.getItem_IMAGE();
        this.item_DETAIL = entity.getItem_DETAIL();
        this.item_SELL = entity.getItem_SELL();
        this.registeredDate = entity.getRegisteredDate();
    }


    @Override
    public int compareTo(ProductResponseDto o) {
        if(this.review_COUNT > o.review_COUNT) return -1;
        else if (this.review_COUNT == o.review_COUNT) {
            return 0;
        }
        return 1;
    }




//    public int compareTo(ProductResponseDto o, String type) {
//        if (type.equals("selling")) {
//            if(this.item_PRICE_DISCOUNT > o.item_PRICE_DISCOUNT) return  -1;
//            else if (this.item_PRICE_DISCOUNT == o.item_PRICE_DISCOUNT) {
//                return 0;
//            }
//        }
//        return 1;
//    }

}
