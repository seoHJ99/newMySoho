package com.study.springboot.client.service;


import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.dto.ReviewResponseDTO;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.client.dto.ReviewResponseDto;
import com.study.springboot.client.dto.ReviewSaveResponsedto;
import com.study.springboot.entity.Product;
import com.study.springboot.entity.ProductRepository;
import com.study.springboot.entity.Review;
import com.study.springboot.entity.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientReviewService_JunTae {
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public ProductResponseDto findById(int item_idx){
        Product product = productRepository.findById(item_idx).get();
        ProductResponseDto productResponseDto = new ProductResponseDto(product);
        return productResponseDto;
    }

    @Transactional(readOnly = true)
    public void SaveReview(ReviewSaveResponsedto dto){
        Review review = dto.toSaveEntity();
        reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByItemId(int item_IDX){
        ProductResponseDto temp = productService.findById(item_IDX);
        List<ReviewResponseDto> all = new ArrayList<>();
        if(temp.getItem_OPTION() != null){
            List<Product> entities= productRepository.findByItem_OPTION(item_IDX);
            for(Product entity: entities){
                System.out.println(entity.getItem_OPTION());
              List<Review> tempList = reviewRepository.findByItemIDX(entity.getItem_idx());
              for(Review s : tempList){
                  ReviewResponseDto dto = new ReviewResponseDto(s);
                  all.add(dto);
              }
            }
        }else {
            List<Review> review = reviewRepository.findByItemIDX(item_IDX);
            for (Review temp2 : review) {
                ReviewResponseDto reviewResponseDto1 = new ReviewResponseDto(temp2);
                all.add(reviewResponseDto1);
            }
        }
        return all;
    }

    @Transactional(readOnly = true)
    public List<ReviewResponseDto> findByMemId(String memberID){
        List<Review> entitys = reviewRepository.findOnlyMembersReview(memberID);
        List<ReviewResponseDto> dtos = new ArrayList<>();
        for(Review review : entitys){
            ReviewResponseDto dto = new ReviewResponseDto(review);
            dtos.add(dto);
        }
        return dtos;
    }
}
