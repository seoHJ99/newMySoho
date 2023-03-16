package com.study.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "review")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int review_IDX;
    private int orders_IDX;
    @Column(name = "item_IDX")
    private int itemIDX;
    private String review_WRITER;
    private int review_SCORE;
    private String review_TITLE;
    private String review_CONTENT;
    private String review_IMAGE;
    @Column(name ="review_REGDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reviewREGDATE = LocalDate.now();
    private String review_REPLY;
    private String review_STATUS = "공개";
    public void addReply(String reply, String status){
        this.review_REPLY = reply;
        this.review_STATUS =status;
    }

    @Builder
    public Review(int review_IDX, int orders_IDX, int itemIDX, String review_WRITER, int review_SCORE, String review_TITLE, String review_CONTENT, String review_IMAGE,  String review_REPLY) {
        this.review_IDX = review_IDX;
        this.orders_IDX = orders_IDX;
        this.itemIDX = itemIDX;
        this.review_WRITER = review_WRITER;
        this.review_SCORE = review_SCORE;
        this.review_TITLE = review_TITLE;
        this.review_CONTENT = review_CONTENT;
        this.review_IMAGE = review_IMAGE;
        this.review_REPLY = review_REPLY;
    }
}
