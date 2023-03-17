package com.study.springboot.client.service;

import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.client.dto.CartInformation;
import com.study.springboot.client.dto.NonmemberSaveDto;
import com.study.springboot.entity.CartRepository;
import com.study.springboot.entity.NonmemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService_Ho {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public List<CartInformation> makeCartDto(int item_list[], int item_quantity[]){
        List<CartInformation> cartDtoList = new ArrayList<>();
        for(int i=0; i<item_list.length; i++){
            ProductResponseDto dtoSmall = productService.findById(item_list[i]);
            int price = dtoSmall.getItem_PRICE();
            int discount = dtoSmall.getItem_DISCOUNT();
            dtoSmall.setItem_PRICE_DISCOUNT((int) (Math.floor((price-(price*discount/100))/100)*100));
            CartInformation cartInformation = CartInformation.builder()
                    .item_IDX(dtoSmall.getItem_idx())
                    .item_OPTION(dtoSmall.getItem_OPTION())
                    .item_COUNT(item_quantity[i])
                    .item_PRICE_DISCOUNT(dtoSmall.getItem_PRICE_DISCOUNT())
                    .item_DISCOUNT(dtoSmall.getItem_DISCOUNT())
                    .item_IMAGE(dtoSmall.getItem_IMAGE())
                    .item_PRICE(dtoSmall.getItem_PRICE())
                    .item_NAME(dtoSmall.getItem_NAME())
                    .build();
            cartDtoList.add(cartInformation);
        }
        return cartDtoList;
    }

}
