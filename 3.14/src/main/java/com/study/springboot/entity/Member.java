package com.study.springboot.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer member_IDX;
    @Column(name = "member_ID")
    private String memberID;
    @Column(name = "member_PW")
    private String memberPw;
    private String member_NAME;
    private String member_MAIL;
    private String member_ADDRESS;
    private String member_POST;
    private String member_PHONE;
    @Column(name = "member_SIGNUP")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate = LocalDate.now();
    private Integer member_POINT =0;
    private String member_ROLE;
    @Column(name = "member_DROP")
    private String status;

    @Builder
    public Member(Integer member_IDX, String memberID, String memberPw, String member_NAME,
                  String member_MAIL, String member_ADDRESS, String member_POST, String member_PHONE, LocalDate joinDate,
                  Integer member_POINT, String member_ROLE, String status) {
        this.member_IDX = member_IDX;
        this.memberID = memberID;
        this.memberPw = memberPw;
        this.member_NAME = member_NAME;
        this.member_MAIL = member_MAIL;
        this.member_ADDRESS = member_ADDRESS;
        this.member_POST = member_POST;
        this.member_PHONE = member_PHONE;
        this.member_POINT = member_POINT;
        this.member_ROLE = member_ROLE;
        this.status = status;
    }
}