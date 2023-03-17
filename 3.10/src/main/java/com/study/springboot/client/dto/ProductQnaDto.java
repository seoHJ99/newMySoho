package com.study.springboot.client.dto;

import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.dto.QnaResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductQnaDto {
    private ProductResponseDto productResponseDto;
    private QnaResponseDto qnaResponseDto;
}
