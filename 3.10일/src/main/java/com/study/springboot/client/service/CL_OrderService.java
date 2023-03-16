package com.study.springboot.client.service;


import com.study.springboot.admin.dto.OrderSaveDto;
import com.study.springboot.client.dto.NonmemberSaveDto;
import com.study.springboot.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@RequiredArgsConstructor
public class CL_OrderService {
    private final OrdersRepository ordersRepository;
    private final NonmemberRepository nonmemberRepository;
    private final OrderDetailRepository orderDetailRepository;

    public int saveNonmember(String name, String phone){
        NonmemberSaveDto dto = new NonmemberSaveDto(name, phone);
        return nonmemberRepository.saveAndFlush(dto.toSaveEntity()).getNonmember_IDX();
    }
    public int orderSave(OrderSaveDto dto){
        Order entity = dto.toSaveEntity();
      return ordersRepository.saveAndFlush(entity).getOrdersIDX();
    }

    public void orderDetailSave(OrderDetailSaveDto dto){
       OrderDetail entity = dto.toSaveEntity();
       orderDetailRepository.save(entity);
    }
}
