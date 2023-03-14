package com.study.springboot.client.service;

import com.study.springboot.client.dto.ProductResponseDto;
import com.study.springboot.entity.Product;
import com.study.springboot.entity.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Cl_SearchService_HyungMin {
    private final ProductRepository productRepository;
    private final Cl_ListService_HyungMin listService;

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProductList(String keyword) {
        List<Product> list =  productRepository.findByKeyword(keyword);
        List<ProductResponseDto> list1 = listService.getPriceDiscount(list);
        return list1;
    }

}
