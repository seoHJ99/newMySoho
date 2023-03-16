package com.study.springboot.client.order;

import com.study.springboot.admin.dto.OrderResponseDto;
import com.study.springboot.admin.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ClientOrderController_Hojun {
    private final OrderService orderService;

//    @RequestMapping()
//    public String orderCart(OrderSaveDto orderSaveDto, MemberJoinDto memberJoinDto){
//        오더는 반드시 들어오고, 맴버는 들어오면 그때 저장하고
//         먼저 회원 가입에 대한 유효성 조사부터
//        오더 번호 저장한 뒤, 오더 디테일 저장해야 함.
//        리스트로 아이템 넘버와 갯수를 받아온 뒤 저장
//        orderService.saveOrder(orderSaveDto);
//        return "/order/complete?idx=";
//    }

    @RequestMapping("/order/complete/{param}")
    public String orderComplete(Model model, @RequestParam("param") int idx){

        return "/client/order/order-complete";
    }

}
