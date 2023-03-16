package com.study.springboot.entity;
import com.study.springboot.admin.dto.ProductResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "item")
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int item_idx;
    private String item_CATEGORY1;
    private String item_CATEGORY2;
    private String item_NAME;
    private int item_PRICE;
    private int item_DISCOUNT;
    @Column(name = "item_ORIGINAL")
    private int itemORIGINAL;
    private int item_AMOUNT;
    private String item_IMAGE;
    private String item_DETAIL;
    private String item_OPTION;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "item_REGDATE")
    private LocalDate registeredDate = LocalDate.now();
    private int item_SELL;

    @Builder
    public Product(int item_PRICE, int item_DISCOUNT, String item_IMAGE, String item_DETAIL, String item_OPTION) {
        this.item_PRICE = item_PRICE;
        this.item_DISCOUNT = item_DISCOUNT;
        this.item_OPTION = item_OPTION;
        this.item_IMAGE = item_IMAGE;
        this.item_DETAIL = item_DETAIL;
    }

    public void toSaveEntity(ProductResponseDto dto) {
        this.item_CATEGORY1 = dto.getItem_CATEGORY1();
        this.item_CATEGORY2 = dto.getItem_CATEGORY2();
        this.item_NAME = dto.getItem_NAME();
        this.item_PRICE = dto.getItem_PRICE();
        this.item_DISCOUNT = dto.getItem_DISCOUNT();
        this.itemORIGINAL = dto.getItem_ORIGINAL();
        this.item_AMOUNT = dto.getItem_AMOUNT();
        this.item_OPTION = dto.getItem_OPTION();
        this.item_IMAGE = dto.getItem_IMAGE();
        this.item_DETAIL = dto.getItem_DETAIL();
        this.item_SELL = dto.getItem_SELL();
    }


    public void toUpdateEntity(ProductResponseDto dto) {
        this.item_idx = dto.getItem_idx();
        this.item_CATEGORY1 = dto.getItem_CATEGORY1();
        this.item_CATEGORY2 = dto.getItem_CATEGORY2();
        this.item_NAME = dto.getItem_NAME();
        this.item_PRICE = dto.getItem_PRICE();
        this.item_DISCOUNT = dto.getItem_DISCOUNT();
        this.itemORIGINAL = dto.getItem_ORIGINAL();
        this.item_IMAGE = dto.getItem_IMAGE();
        this.item_DETAIL = dto.getItem_DETAIL();
        this.item_SELL = dto.getItem_SELL();
    }
}