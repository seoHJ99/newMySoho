package com.study.springboot.client.service;


import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.dto.ReviewResponseDTO;
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
        List<Review> review = reviewRepository.findByItemIDX(item_IDX);
        List<ReviewResponseDto> reviewResponseDto = new ArrayList<>();
        for ( Review temp : review){
            ReviewResponseDto reviewResponseDto1 = new ReviewResponseDto(temp);
            reviewResponseDto.add(reviewResponseDto1);
        }
        return reviewResponseDto;
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
