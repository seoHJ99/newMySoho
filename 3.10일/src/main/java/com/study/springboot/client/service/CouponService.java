package com.study.springboot.client.service;

import com.study.springboot.admin.dto.CouponResoponseDTO;
import com.study.springboot.entity.Coupon;
import com.study.springboot.entity.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;

    public List<CouponResoponseDTO> findCouponByMemberIDX(int memSession) {

        List<Coupon> couponList = couponRepository.findByMemberIDX(memSession);
        List<CouponResoponseDTO> list = new ArrayList<>();

        for(Coupon entity : couponList){
            CouponResoponseDTO dto = new CouponResoponseDTO(entity);
            list.add(dto);
        }
        return list;
    }
}
