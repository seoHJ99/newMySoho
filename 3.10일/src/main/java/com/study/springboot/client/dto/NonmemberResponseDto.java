package com.study.springboot.client.dto;

import com.study.springboot.entity.Nonmember;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// made by 형민
public class NonmemberResponseDto {

    private int idx;
    private String name;
    private String phone;

    public NonmemberResponseDto(Nonmember entity) {
        this.idx = entity.getNonmember_IDX();
        this.name = entity.getNonmember_NAME();
        this.phone = entity.getNonmember_PHONE();
    }
}
