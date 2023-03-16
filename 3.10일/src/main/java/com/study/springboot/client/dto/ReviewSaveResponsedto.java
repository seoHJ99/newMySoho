package com.study.springboot.client.dto;

import com.study.springboot.entity.Review;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReviewSaveResponsedto {

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


    public Review toSaveEntity() {
        return Review.builder()
                .review_IDX(this.review_IDX)
                .itemIDX(this.item_IDX)
                .review_WRITER(review_WRITER)
                .review_IMAGE(review_IMAGE)
                .review_CONTENT(this.review_CONTENT)
                .review_TITLE(this.review_TITLE)
                .review_SCORE(this.review_SCORE)
                .build();
    }
}
