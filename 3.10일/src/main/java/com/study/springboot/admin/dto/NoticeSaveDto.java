package com.study.springboot.admin.dto;

import com.study.springboot.entity.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class NoticeSaveDto {
    private int notice_IDX = 0;
    private String notice_CATE;
    private String notice_WRITER;
    private String notice_TITLE;
    private String notice_CONTENT;
    private String notice_REGTYPE;
    private String notice_FILE;
    private LocalDateTime noticeREGDATE =LocalDateTime.now();

    public Notice toSaveEntity(){
        return Notice.builder()
                .notice_CATE(notice_CATE)
                .notice_CONTENT(notice_CONTENT)
                .notice_FILE(notice_FILE)
                .notice_REGTYPE(notice_REGTYPE)
                .notice_TITLE(notice_TITLE)
                .noticeREGDATE(noticeREGDATE)
                .build();
    }
    public Notice toUpdateEntity(){
        return Notice.builder()
                        .noticeREGDATE(noticeREGDATE)
                                .notice_TITLE(notice_TITLE)
                                        .notice_FILE(notice_FILE)
                                                .notice_CONTENT(notice_CONTENT)
                                                        .notice_CATE(notice_CATE)
                .notice_REGTYPE(notice_REGTYPE)
                .notice_IDX(notice_IDX)
                                                                .build();
    }

}
