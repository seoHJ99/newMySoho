package com.study.springboot.entity;


import com.study.springboot.admin.dto.QnaResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Qna")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Qna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qna_IDX;
    private int item_IDX;
    private String qna_CATE;
    private String qna_WRITER;
    private Integer member_IDX;
    private String qna_PW;
    private String qna_CONTENT;
    private String qna_REACT;
    private String qna_ANSWERED;
    private String qna_SORT;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "qna_REGDATE")
    private LocalDate registeredDate = LocalDate.now();
    private int qna_SECRET;

    @Builder

    public Qna(String qna_REACT) {
        this.qna_REACT = qna_REACT;
    }
    public void toSaveEntity(QnaResponseDto dto){
        this.qna_IDX = dto.getQna_IDX();
        this.item_IDX = dto.getItem_IDX();
        this.qna_CATE = dto.getQna_CATE();
        this.qna_WRITER = dto.getQna_WRITER();
        this.member_IDX = dto.getMember_IDX();
        this.qna_PW = dto.getQna_PW();
        this.qna_CONTENT = dto.getQna_CONTENT();
        this.qna_REACT = dto.getQna_REACT();
        this.qna_ANSWERED = dto.getQna_ANSWERED();
        this.qna_SECRET = dto.getQna_SECRET();
        this.qna_SORT = dto.getQna_SORT();
    }
}
