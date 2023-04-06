package com.study.springboot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewMine {
    private ReviewResponseDto reviewResponseDto;
    private String item_Image;
    private String item_Name;
    private int item_idx;
}
