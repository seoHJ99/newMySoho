package com.study.springboot.client.service;



import com.study.springboot.admin.dto.ReviewResponseDTO;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.admin.service.ReviewService;
import com.study.springboot.client.dto.ProductResponseDto;
import com.study.springboot.entity.Product;

import com.study.springboot.entity.ProductRepository;


import com.study.springboot.entity.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.PrivateKey;
import java.util.*;

@Service
@RequiredArgsConstructor
public class Cl_ListService_HyungMin {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    
    // 페이징 처리
    @Transactional(readOnly = true)
    public Page<Product> findProductList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("registeredDate"));
        Pageable pageable = PageRequest.of(page, 12, Sort.by(sorts));
        return productRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllPDNoRepeat(){

        List<Product> list = productRepository.findAll();
        List<ProductResponseDto> dtoList = getPriceDiscount(list);
        return dtoList;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> getPriceDiscount(List<Product> list){
        List<ProductResponseDto> dtoList = new ArrayList<>();
        for (Product temp : list) {
            ProductResponseDto product = new ProductResponseDto(temp);
            int price = product.getItem_PRICE();
            int discount = product.getItem_DISCOUNT();
            product.setItem_PRICE_DISCOUNT((int) (Math.floor((price - (price * discount / 100)) / 100) * 100));
            dtoList.add(product);
        }
        return dtoList;
    }


    // 상품 리스트 전체 가져오기
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProductList(){

        List<Product> list = productRepository.findAllNoRepeat();
        List<ProductResponseDto> dtoList = getPriceDiscount(list);

        return dtoList;
    }

    // 카테고리별로 상품 리스트 가져오기
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findProductListByCate(String category){
        List<Product> list;
        if(category.equals("SALE")) {
            list =  productRepository.findByCategory2(category);
        } else {
            list =  productRepository.findByCategory(category);
        }
        List<ProductResponseDto> list1 = getPriceDiscount(list);
        return list1;
    }

    // 작성자 서호준
    // 등록일, 판매량, 리뷰, 별점, 가격순 정렬기능 구현
    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByDate(){
        List<Product> entityList = productRepository.pdOrderByDate();
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderBySelling(){
        List<Product> entityList = productRepository.pdOrderBySelling();
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByPrice(){
        List<Product> entityList = productRepository.pdOrderByPrice();
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByReview(){
        List<Product> entityList = productRepository.findAllNoRepeat();
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        for(int i=0; i<entityList.size(); i++) {
            int count = reviewRepository.itemReviewCount(entityList.get(i).getItem_idx());
            ProductResponseDto dto = list.get(i);
            dto.setReview_COUNT(count);
        }
        Collections.sort(list);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByScore(){
        List<Product> entityList = productRepository.findAll();
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        for(int i=0; i<entityList.size(); i++) {
            ProductResponseDto dto = new ProductResponseDto(entityList.get(i));
            int scoreSum = 0;
            int item_idx = entityList.get(i).getItem_idx();
            List<ReviewResponseDTO> reviewList = productService.findReviewScore(item_idx);
            for(int j=0; reviewList.size()>j; j++){
                int score = reviewList.get(i).getReview_SCORE();
                scoreSum = scoreSum + score;
            }
            if(reviewList.size()==0) {
                dto.setReview_SCORE(0);
            }else {
                int scoreAvg = scoreSum / reviewList.size();
                dto.setReview_SCORE(scoreAvg);
            }
            list.add(dto);
        }
        return list;
    }

    // 검색된 결과 정렬 기능
    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByDate(String keyword){
        List<Product> entityList = productRepository.pdOrderByDate(keyword);
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderBySelling(String keyword){
        List<Product> entityList = productRepository.pdOrderBySelling(keyword);
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByPrice(String keyword){
        List<Product> entityList = productRepository.pdOrderByPrice(keyword);
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByReview(String keyword){
        List<Product> entityList = productRepository.findByKeyword(keyword);
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        for(int i=0; i<entityList.size(); i++) {
            int count = reviewRepository.itemReviewCount(entityList.get(i).getItem_idx());
            ProductResponseDto dto = list.get(i);
            dto.setReview_COUNT(count);
        }
        Collections.sort(list);
        return list;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> pdOrderByScore(String keyword){
        List<Product> entityList = productRepository.findByKeyword(keyword);
        List<ProductResponseDto> list = getPriceDiscount(entityList);
        for(int i=0; i<entityList.size(); i++) {
            ProductResponseDto dto = new ProductResponseDto(entityList.get(i));
            int scoreSum = 0;
            int item_idx = entityList.get(i).getItem_idx();
            List<ReviewResponseDTO> reviewList = productService.findReviewScore(item_idx);
            for(int j=0; reviewList.size()>j; j++){
                int score = reviewList.get(i).getReview_SCORE();
                scoreSum = scoreSum + score;
            }
            if(reviewList.size()==0) {
                dto.setReview_SCORE(0);
            }else {
                int scoreAvg = scoreSum / reviewList.size();
                dto.setReview_SCORE(scoreAvg);
            }
            list.add(dto);
        }
        return list;
    }
}
