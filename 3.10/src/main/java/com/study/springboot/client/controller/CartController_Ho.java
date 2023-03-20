package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.MemberResponseDTO;
import com.study.springboot.admin.dto.OrderResponseDto;
import com.study.springboot.admin.dto.OrderSaveDto;
import com.study.springboot.admin.service.MemberService;
import com.study.springboot.admin.service.OrderService;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.client.dto.CartInformation;
import com.study.springboot.client.dto.MemberJoinDto;
import com.study.springboot.client.dto.NonmemberResponseDto;
import com.study.springboot.client.service.*;
import com.study.springboot.entity.MemberListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CartController_Ho {
    private final ProductService productService;
    private final CL_OrderService orderService;
    private final OrderService orderService2;
    private final MemberService memberService;
    private final NonmemberService nonmemberService;
    private final MemberListRepository memberListRepository;
    private final Cl_MemberService clMemberService;
    private final CartService_Ho cartService;


    @RequestMapping("/cart") // 장바구니 구현
    public String cartView(Model model, @RequestParam("item_idx") int item_list[],
                           @RequestParam("item_quantity") int item_quantity[],
                           HttpSession session){
        List<CartInformation> cartDtoList = cartService.makeCartDto(item_list, item_quantity);
        Optional<Object> memberIdx = Optional.ofNullable(session.getAttribute("member_IDX"));
        memberIdx.ifPresent( idx ->
                                    {
                                        MemberResponseDTO memberResponseDTO = null;
                                        try {
                                            memberResponseDTO = memberService.findByIDX((int)idx);
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                        model.addAttribute("member",memberResponseDTO);
                                    });
        model.addAttribute("item",cartDtoList);
        return "/client/theOthers/shopping-cart";
    }

    @RequestMapping("/order/save")
    public String saveOrder(OrderSaveDto orderSaveDto,
                            int [] item_QTY,
                            int [] item_IDX,
                            HttpServletRequest request,
                            Model model,
                            HttpSession session,
                            MemberJoinDto memberDto,
                            BindingResult bindingResult) throws Exception {
        orderSaveDto = cartService.setCartInfoToOrderDto(orderSaveDto, request);
        int nonmemberIdx = 0;
        int orderIdx =0;
        OrderDetailSaveDto orderDetailSaveDto = new OrderDetailSaveDto();
        if(session.getAttribute("member_IDX") == null){
            // 첫 주문인 비회원
            if(nonmemberService.findNonmember(orderSaveDto.getOrders_NAME(), orderSaveDto.getOrders_PHONE()) == null) {
                orderSaveDto.setAccumulate_MILEAGE(0);
                int nonmemberIDX = cartService.saveNonmember(orderSaveDto);
                orderSaveDto.setNonmember_IDX(nonmemberIdx);
                orderIdx = orderService.orderSave(orderSaveDto);
                cartService.orderDetailSave(orderIdx,item_IDX,item_QTY);
            }else { // 첫 주문이 아닌 비회원
                NonmemberResponseDto NonMemDto = nonmemberService.findNonmember(orderSaveDto.getOrders_NAME(), orderSaveDto.getOrders_PHONE());
                orderSaveDto.setNonmember_IDX(NonMemDto.getIdx());
                orderIdx = orderService.orderSave(orderSaveDto);
                cartService.orderDetailSave(orderIdx,item_IDX,item_QTY);
            }
        }else{ // 회원이 주문할때
            orderSaveDto.setMember_IDX((int)session.getAttribute("member_IDX"));
            orderIdx = orderService.orderSave(orderSaveDto);
            cartService.orderDetailSave(orderIdx,item_IDX,item_QTY);
            MemberResponseDTO memberResponseDTO = memberService.findByIDX(((int)session.getAttribute("member_IDX")));
            int mileage = orderSaveDto.getAccumulate_MILEAGE() - orderSaveDto.getUsing_MILEAGE();
            memberResponseDTO.setMember_POINT(memberResponseDTO.getMember_POINT() + mileage);
            memberService.changeMemberInfo(memberResponseDTO);
        }
        OrderResponseDto dto = orderService2.findOrderDto(orderIdx);
        model.addAttribute("order",dto);

        if (session.getAttribute("memberID") == null) {
            memberDto =  clMemberService.setJoinInfoFromOrder(orderSaveDto);
            clMemberService.userSave(memberDto, bindingResult);
            HttpStatus status = HttpStatus.OK;
            if (status == HttpStatus.OK) System.out.println("회원가입 성공!");
        }

        return "/client/order/order-complete";
    }
}
