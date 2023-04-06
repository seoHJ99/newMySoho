package com.study.springboot.admin.dto;

import com.study.springboot.entity.Member;
import com.study.springboot.entity.Qna;
import lombok.*;

import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class QnaResponseDto {
    // dto 선언
    private int qna_IDX;
    private int item_IDX;
    // 아이템과 연관된 질문일 경우에만 존재
    // 아이템과 상관없는 질문 ex) 회원탈퇴 시켜주세요  와 같은 문의일때는
    // null상태

    private String qna_CATE;
    private String qna_WRITER;
    private String qna_PW;
    private String qna_SORT;
    private String qna_CONTENT;
    private String qna_ANSWERED;
    private String qna_REACT;
    private LocalDate qna_REGDATE;
    private Integer member_IDX;
    private int qna_SECRET;

    public QnaResponseDto (Qna entity){
        this.qna_IDX = entity.getQna_IDX();
        this.item_IDX = entity.getItem_IDX();
        this.qna_CATE = entity.getQna_CATE();
        this.qna_WRITER = entity.getQna_WRITER();
        this.member_IDX = entity.getMember_IDX();
        this.qna_PW = entity.getQna_PW();
        this.qna_CONTENT = entity.getQna_CONTENT();
        this.qna_ANSWERED = entity.getQna_ANSWERED();
        this.qna_REACT = entity.getQna_REACT();
        this.qna_REGDATE = entity.getRegisteredDate();
        this.qna_SECRET = entity.getQna_SECRET();
        this.qna_SORT = entity.getQna_SORT();

    }

}
