package com.study.springboot.admin.controller;


import com.study.springboot.entity.OrderDetail;
import com.study.springboot.admin.dto.OrderInfoDto;
import com.study.springboot.admin.service.OrderService;
import com.study.springboot.admin.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class OrderController {
    private final OrderService orderService;

//    @RequestMapping("/order/{idx}")
//    public String order(@PathVariable("idx") int idx, Model model){
//        model.addAttribute("order" ,orderService.findByOrderId(idx));
//        return "orderDetailPage16";
//    }

    @RequestMapping("/order")
    public String orderDetail(Integer idx, Model model){

        ArrayList<OrderInfoDto> info = new ArrayList<>();
        List<ProductResponseDto> orderProductsInfo =
        orderService.findOrderItems(orderService.findOrderDetailByOrderId(idx));
        System.out.println("aaaaaaaaaaaaaaa");
        List<OrderDetail> count = orderService.findOrderDetailByOrderId(idx);

        for(int i=0; i<orderProductsInfo.size(); i++){
            OrderInfoDto orderInfoDto = new OrderInfoDto();
            orderInfoDto.setItem_name(orderProductsInfo.get(i).getItem_NAME());
            System.out.println(orderInfoDto.getItem_name());
           orderInfoDto.setItem_price(orderProductsInfo.get(i).getItem_PRICE());
           orderInfoDto.setItem_quantity(count.get(i).getOdetail_QTY());
           orderInfoDto.setItem_total(orderInfoDto.getItem_quantity() * orderInfoDto.getItem_price());
           orderInfoDto.setItem_status(count.get(i).getOdetail_STATUS());
           info.add(orderInfoDto);
        }
        int totalPrice =0;
        for(int i=0; i<info.size(); i++){
            totalPrice += info.get(i).getItem_total();
        }
        System.out.println(totalPrice);


        model.addAttribute("order", orderService.findByOrderId(idx));
        model.addAttribute("list",info);
        model.addAttribute("total", totalPrice);
        return "orderDetailPage16";
    }




}
