package com.study.springboot.admin.dto;


import com.study.springboot.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductSaveRequestDto {
    private int item_PRICE;
    private int item_DISCOUNT;
    private String item_IMAGE;
    private String item_DETAIL;

//    @Builder
//
//    public ProductSaveRequestDto(int item_PRICE, int item_DISCOUNT, String item_IMAGE, String item_DETAIL) {
//
//        this.item_PRICE = item_PRICE;
//        this.item_DISCOUNT = item_DISCOUNT;
//        this.item_IMAGE = item_IMAGE;
//        this.item_DETAIL = item_DETAIL;
//    }

    public Product toEntity(){
        return Product.builder()
                .item_PRICE(item_PRICE)
                .item_DISCOUNT(item_DISCOUNT)
                .item_IMAGE(item_IMAGE)
                .item_DETAIL(item_DETAIL)
                .build();
    }
}
