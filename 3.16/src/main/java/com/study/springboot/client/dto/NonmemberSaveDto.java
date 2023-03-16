package com.study.springboot.client.dto;

import com.study.springboot.entity.Nonmember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NonmemberSaveDto {
    private String name;
    private String phone;

    public Nonmember toSaveEntity(){
     return Nonmember.builder()
             .nonmember_PHONE(phone)
             .nonmember_NAME(name)
             .build();
    }

}
