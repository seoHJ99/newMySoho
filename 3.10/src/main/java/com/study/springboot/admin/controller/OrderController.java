package com.study.springboot.admin.controller;

import com.study.springboot.admin.dto.OrderDetailDto;
import com.study.springboot.entity.OrderDetail;
import com.study.springboot.admin.dto.OrderInfoDto;
import com.study.springboot.admin.service.OrderService;
import com.study.springboot.admin.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin")
public class OrderController {
    private final OrderService orderService;


    @RequestMapping("/order")
    public String orderDetail(Integer idx, Model model){

        List<ProductResponseDto> orderProductsInfo =
        orderService.findOrderItems(orderService.findOrderDetailByOrderId(idx));
        List<OrderDetail> count = orderService.findOrderDetailByOrderId(idx);
       ArrayList<OrderInfoDto> info = orderService.setInfo(orderProductsInfo, count);
        int totalPrice =0;
        for(int i=0; i<info.size(); i++){
            totalPrice += info.get(i).getItem_total();
        }
        model.addAttribute("order", orderService.findByOrderId(idx));
        model.addAttribute("list",info);
        model.addAttribute("total", totalPrice);
        return "orderDetailPage16";
    }

    @RequestMapping("/order/modify")
    public String orderModify(@RequestParam("several_status") String status[], @RequestParam("orders_IDX") int ordersIDX){
        List<OrderDetail> orderDetails =  orderService.findOrderDetailByOrderId(ordersIDX);
        for(int i=0; i<orderDetails.size(); i++){
            OrderDetailDto orderDetailDto = new OrderDetailDto(orderDetails.get(i));
            orderDetailDto.setOdetail_STATUS(status[i]);
            orderService.modifyOdetail(orderDetailDto);
        }
        return "redirect:/admin/list/order";
    }
}
