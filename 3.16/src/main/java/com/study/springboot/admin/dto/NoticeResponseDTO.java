package com.study.springboot.admin.dto;


import com.study.springboot.entity.Notice;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class NoticeResponseDTO {

    private int notice_IDX;
    private String notice_CATE;
    private String notice_WRITER = "운영자";
    private String notice_TITLE;
    private String notice_CONTENT;
    private String notice_FILE;
    private LocalDateTime noticeREGDATE;
    private String notice_REGTYPE;

    public NoticeResponseDTO(Notice entity) {
        this.notice_REGTYPE = entity.getNotice_REGTYPE();
        this.notice_IDX = entity.getNotice_IDX();
        this.notice_CATE = entity.getNotice_CATE();
        this.notice_WRITER = entity.getNotice_WRITER();
        this.notice_TITLE = entity.getNotice_TITLE();
        this.notice_CONTENT = entity.getNotice_CONTENT();
        this.noticeREGDATE = entity.getNoticeREGDATE();
    }

}
