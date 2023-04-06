package com.study.springboot.client.service;


import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.entity.Product;
import com.study.springboot.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class Cl_ProductService_HyungMin {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductResponseDto findById(int item_idx){
        Product product = productRepository.findById(item_idx).get();

        ProductResponseDto productResponseDto2 = new ProductResponseDto(product);

        int price = productResponseDto2.getItem_PRICE();
        int discount = productResponseDto2.getItem_DISCOUNT();
        productResponseDto2.setItem_PRICE_DISCOUNT((int) (Math.floor((price-(price*discount/100))/100)*100));

        return productResponseDto2;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findByAllConnection(int item_idx){
        List<ProductResponseDto> dto = new ArrayList<>();
        List<Product> product = productRepository.findByItem_OPTION(item_idx);
        for(Product entity : product) {

            ProductResponseDto productResponseDto2 = new ProductResponseDto(entity);
            int price = productResponseDto2.getItem_PRICE();
            int discount = productResponseDto2.getItem_DISCOUNT();
            productResponseDto2.setItem_PRICE_DISCOUNT((int) (Math.floor((price - (price * discount / 100)) / 100) * 100));
            dto.add(productResponseDto2);
        }
        return dto;
    }
}
