package com.study.springboot.client.dto;

import com.study.springboot.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MemberJoinDto {
    @Nullable
    private Integer member_IDX;
    @NotBlank(message = "member_ID null, 빈문자열, 스페이스문자열만을 넣을 수 없습니다.")
    private String memberID;
    @NotBlank(message = "member_PW null, 빈문자열, 스페이스문자열만을 넣을 수 없습니다.")
    private String memberPw;
    private String member_NAME;
    private String member_MAIL;
    private String member_ADDRESS;
    private String member_POST;
    private String member_PHONE;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate member_SIGNUP;
    private Integer member_POINT = 0;
    private String member_ROLE;
    private String status;

    public Member toSaveEntity() {
        return Member.builder()
                .memberID(memberID)
                .member_POINT(member_POINT)
                .memberPw(memberPw)
                .member_NAME(member_NAME)
                .member_MAIL(member_MAIL)
                .member_ADDRESS(member_ADDRESS)
                .member_POST(member_POST)
                .member_PHONE(member_PHONE)
                .joinDate(member_SIGNUP)
                .member_ROLE(member_ROLE)
                .status(status)
                .build();
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
}