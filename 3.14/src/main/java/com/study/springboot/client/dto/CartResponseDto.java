package com.study.springboot.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {
    private int cart_IDX;
    private int member_IDX;
    private int item_IDX;
    private int nonmember_IDX;
    private int cart_QTY;
}
