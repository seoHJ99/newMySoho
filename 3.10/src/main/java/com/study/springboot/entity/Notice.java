package com.study.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int notice_IDX;
    private String notice_CATE;
    private String notice_WRITER = "운영자";
    private String notice_TITLE;
    private String notice_CONTENT;
    private String notice_FILE; // ck에디터 사용하는 방법 공부
    private String notice_REGTYPE;
    @Column(name = "notice_REGDATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime noticeREGDATE;

    @Builder
    public Notice(int notice_IDX, String notice_CATE, String notice_TITLE, String notice_CONTENT, String notice_FILE, String notice_REGTYPE, LocalDateTime noticeREGDATE) {
        this.notice_CATE = notice_CATE;
        this.notice_TITLE = notice_TITLE;
        this.notice_CONTENT = notice_CONTENT;
        this.notice_FILE = notice_FILE;
        this.noticeREGDATE = noticeREGDATE;
        this.notice_REGTYPE = notice_REGTYPE;
        this.notice_IDX = notice_IDX;
    }
}


