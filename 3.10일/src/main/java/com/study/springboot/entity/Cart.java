package com.study.springboot.entity;

import com.study.springboot.client.dto.CartResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "cart")
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cart_IDX;
    private int member_IDX;
    private int item_IDX;
    private int nonmember_IDX;
    private int cart_QTY;

    public void toSaveEntity(CartResponseDto dto){
        this.cart_IDX = dto.getCart_IDX();

    }
}

