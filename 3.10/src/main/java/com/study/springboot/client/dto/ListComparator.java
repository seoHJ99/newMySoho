package com.study.springboot.client.dto;

import java.util.Comparator;

public class ListComparator implements Comparator<ProductResponseDto> {

    @Override
    public int compare(ProductResponseDto o1, ProductResponseDto o2) {
        int price1 = o1.getItem_PRICE_DISCOUNT();
        int price2 = o2.getItem_PRICE_DISCOUNT();

        if (price1 > price2) {
            return 1;
        } else if (price1 < price2) {
            return -1;
        } else {
            return 0;
        }
    }
}
