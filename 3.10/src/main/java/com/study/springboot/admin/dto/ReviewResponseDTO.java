package com.study.springboot.admin.dto;


import com.study.springboot.entity.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class ReviewResponseDTO {
    private int review_IDX;
    private int orders_IDX;
    private int item_IDX;
    private String review_WRITER;
    private int review_SCORE;
    private String review_TITLE;
    private String review_CONTENT;
    private String review_IMAGE;
    private LocalDate review_REGDATE;
    private String review_REPLY;
    private String review_STATUS;

    public ReviewResponseDTO(Review entity) {
        this.review_IDX = entity.getReview_IDX();
        this.orders_IDX = entity.getOrders_IDX();
        this.review_SCORE = entity.getReview_SCORE();
        this.review_WRITER = entity.getReview_WRITER();
        this.review_TITLE = entity.getReview_TITLE();
        this.review_REGDATE = entity.getReviewREGDATE();
        this.item_IDX = entity.getItemIDX();
        this.review_STATUS = entity.getReview_STATUS();
    }

    public void toViewDto(Review entity) {
        this.review_IDX = entity.getReview_IDX();
        this.orders_IDX = entity.getOrders_IDX();
        this.item_IDX = entity.getOrders_IDX();
        this.review_WRITER = entity.getReview_WRITER();
        this.review_SCORE = entity.getReview_SCORE();
        this.review_TITLE = entity.getReview_TITLE();
        this.review_CONTENT = entity.getReview_CONTENT();
        this.review_IMAGE = entity.getReview_IMAGE();
        this.review_REGDATE = entity.getReviewREGDATE();
        this.review_REPLY = entity.getReview_REPLY();
    }
}
