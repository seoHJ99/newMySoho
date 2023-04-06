package com.study.springboot.client.service;

import com.study.springboot.client.dto.NonmemberSaveDto;
import com.study.springboot.entity.CartRepository;
import com.study.springboot.entity.NonmemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CartService_Ho {
    private final CartRepository cartRepository;
    // 비회원은 쿠키로. 회원은 db로


}
