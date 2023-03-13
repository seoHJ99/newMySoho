package com.study.springboot.admin.dto;


import com.study.springboot.entity.Member;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class MemberResponseDTO {
    private Integer member_IDX;
    private String memberID;
    private String memberPw;
    private String member_NAME;
    private String member_MAIL;
    private String member_ADDRESS;
    private String member_POST;
    private String member_PHONE;
    private LocalDate member_SIGNUP;
    private Integer member_POINT;
    private String member_ROLE;
    private String status;



    public MemberResponseDTO(Member entity) {
        this.member_IDX = entity.getMember_IDX();
        this.memberID = entity.getMemberID();
        this.memberPw = entity.getMemberPw();
        this.member_NAME = entity.getMember_NAME();
        this.member_MAIL = entity.getMember_MAIL();
        this.member_ADDRESS = entity.getMember_ADDRESS();
        this.member_POST = entity.getMember_POST();
        this.member_PHONE = entity.getMember_PHONE();
        this.member_SIGNUP = entity.getJoinDate();
        this.member_POINT = entity.getMember_POINT();
        this.member_ROLE = entity.getMember_ROLE();
        this.status = entity.getStatus();
    }

    public Member toUpdateEntity() {
        return Member.builder()
                .member_IDX(member_IDX)
                .memberID(memberID)
                .memberPw(memberPw)
                .member_NAME(member_NAME)
                .member_MAIL(member_MAIL)
                .member_ADDRESS(member_ADDRESS)
                .member_POST(member_POST)
                .member_PHONE(member_PHONE)
                .joinDate(member_SIGNUP)
                .member_POINT(member_POINT)
                .member_ROLE(member_ROLE)
                .status(status)
                .build();
    }

    public Member toUpdateUserEntity() {
        return Member.builder()
                .member_IDX(member_IDX)
                .memberID(memberID)
                .memberPw(memberPw)
                .member_NAME(member_NAME)
                .member_MAIL(member_MAIL)
                .member_ADDRESS(member_ADDRESS)
                .member_POST(member_POST)
                .member_PHONE(member_PHONE)
                .joinDate(member_SIGNUP)
                .member_ROLE(member_ROLE)
                .build();
    }

}
