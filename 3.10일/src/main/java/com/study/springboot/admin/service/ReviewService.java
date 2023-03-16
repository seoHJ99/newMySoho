package com.study.springboot.admin.service;


import com.study.springboot.entity.Review;
import com.study.springboot.entity.ReviewRepository;
import com.study.springboot.admin.dto.ReviewResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public ReviewResponseDTO findReview(int idx){
        ReviewResponseDTO dto = new ReviewResponseDTO();
        Review entity = reviewRepository.findById(idx).get();
        dto.toViewDto(entity);
        return dto;
    }



    public ReviewResponseDTO replyModify(int idx, String reply, String status){
        Review entity = reviewRepository.findById(idx).get();
        entity.addReply(reply, status);
        reviewRepository.save(entity);
        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.toViewDto(entity);
        return dto;
    }
    public void reviewDelete(int id){
        reviewRepository.deleteById(id);
    }
}
