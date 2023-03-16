package com.study.springboot.admin.dto;


import com.study.springboot.entity.Qna;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QnaSaveRequestDto {
    private String qna_REACT;

    public Qna toEntity(){
        return Qna.builder()
                .qna_REACT(qna_REACT)
                .build();
    }
}
