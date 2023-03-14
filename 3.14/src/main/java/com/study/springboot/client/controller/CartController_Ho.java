package com.study.springboot.client.controller;

import com.study.springboot.admin.dto.MemberResponseDTO;
import com.study.springboot.admin.dto.OrderResponseDto;
import com.study.springboot.admin.dto.OrderSaveDto;
import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.service.MemberService;
import com.study.springboot.admin.service.OrderService;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.client.dto.CartInformation;
import com.study.springboot.client.dto.NonmemberResponseDto;
import com.study.springboot.client.service.CL_OrderService;
import com.study.springboot.client.service.NonmemberService;
import com.study.springboot.client.service.OrderDetailSaveDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.ArrayList;
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

    @RequestMapping("/cart/nonmember") // 이건 뭐냐
    @ResponseBody
    public String nonmemberCart(@RequestParam("item") int item [][]){
        int item_idx [] = new int[item.length];
        List<ProductResponseDto> list = new ArrayList<>();
        int item_quantity [] = new int[item.length];
        for(int i =0; i<item_idx.length; i++) {
                item_idx[i] = item[i][0];
                item_quantity[i] = item[i][1];
             list.add( productService.findById(item_idx[i]));
        }
        System.out.println(list.get(0).getItem_NAME());
        return "i";
    }

    @RequestMapping("/cart") // 장바구니 구현
    public String cartView(Model model, @RequestParam("item_idx") int item_list[],
                           @RequestParam("item_quantity") int item_quantity[],
                           HttpSession session){
        List<CartInformation> cartDtoList = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for(int i=0; i<item_list.length; i++){
            ProductResponseDto dtoSmall = productService.findById(item_list[i]);
            int price = dtoSmall.getItem_PRICE();
            int discount = dtoSmall.getItem_DISCOUNT();
            dtoSmall.setItem_PRICE_DISCOUNT((int) (Math.floor((price-(price*discount/100))/100)*100));
            CartInformation cartInformation = CartInformation.builder()
                    .item_IDX(dtoSmall.getItem_idx())
                    .item_OPTION(dtoSmall.getItem_OPTION())
                    .item_COUNT(item_quantity[i])
                    .item_PRICE_DISCOUNT(dtoSmall.getItem_PRICE_DISCOUNT())
                    .item_DISCOUNT(dtoSmall.getItem_DISCOUNT())
                    .item_IMAGE(dtoSmall.getItem_IMAGE())
                    .item_PRICE(dtoSmall.getItem_PRICE())
                    .item_NAME(dtoSmall.getItem_NAME())
                    .build();
            cartDtoList.add(cartInformation);
        }
        Optional<Object> memberIdx = Optional.ofNullable(session.getAttribute("member_IDX"));
        memberIdx.ifPresent( idx ->
                                    {MemberResponseDTO memberResponseDTO = memberService.findByIDX((int)idx);
                                        model.addAttribute("member",memberResponseDTO);
                                    });
        model.addAttribute("item",cartDtoList);
        return "/client/theOthers/shopping-cart";
    }

    @RequestMapping("/order/save")
    public String saveOrder(OrderSaveDto orderSaveDto,
                            int [] item_QTY,
                            int [] item_IDX,
                            @RequestParam("delivery_address1") String address1,
                            @RequestParam("delivery_address2") String address2,
                            @RequestParam("delivery_address3") String address3,
                            @RequestParam("sender_phone1") String s_phone1,
                            @RequestParam("sender_phone2") String s_phone2, // 호준
                            @RequestParam("phone1") String phone1,
                            @RequestParam("phone2")String phone2,
                            Model model,
                            HttpSession session){
        orderSaveDto.setOrders_PHONE(phone1+phone2);
        orderSaveDto.setSenders_PHONE(s_phone1+s_phone2);
        orderSaveDto.setOrders_ADDRESS(address1+","+address2+","+address3);
        if(orderSaveDto.getOrders_PAYMENT().equals("무통장결제")){
            orderSaveDto.setOrders_STATUS("입금대기");
        }else {
            orderSaveDto.setOrders_STATUS("배송준비");
        }
        int nonmemberIdx = 0;
        int orderIdx =0;
        OrderDetailSaveDto orderDetailSaveDto = new OrderDetailSaveDto();
        if(session.getAttribute("member_IDX") == null){
            if(nonmemberService.findNonmember(orderSaveDto.getOrders_NAME(), orderSaveDto.getOrders_PHONE()) == null) {
                orderSaveDto.setAccumulate_MILEAGE(0);
                nonmemberIdx = orderService.saveNonmember(orderSaveDto.getOrders_NAME(), orderSaveDto.getOrders_PHONE());
                orderSaveDto.setNonmember_IDX(nonmemberIdx);
                orderIdx = orderService.orderSave(orderSaveDto);
                for (int i = 0; i < item_QTY.length; i++) {
                    orderDetailSaveDto.setOdetail_QTY(item_QTY[i]);
                    orderDetailSaveDto.setItem_IDX(item_IDX[i]);
                    orderDetailSaveDto.setOdetail_STATUS("기본");
                    orderDetailSaveDto.setOrdersIDX(orderIdx);
                    productService.plusSell(item_IDX[i], item_QTY[i]);
                    orderService.orderDetailSave(orderDetailSaveDto);
                }
            }else {
                NonmemberResponseDto NonMemDto = nonmemberService.findNonmember(orderSaveDto.getOrders_NAME(), orderSaveDto.getOrders_PHONE());
                orderSaveDto.setNonmember_IDX(NonMemDto.getIdx());
                orderIdx = orderService.orderSave(orderSaveDto);
                for (int i = 0; i < item_QTY.length; i++) {
                    orderDetailSaveDto.setOdetail_QTY(item_QTY[i]);
                    orderDetailSaveDto.setItem_IDX(item_IDX[i]);
                    orderDetailSaveDto.setOdetail_STATUS("기본");
                    orderDetailSaveDto.setOrdersIDX(orderIdx);
                    productService.plusSell(item_IDX[i], item_QTY[i]);
                    orderService.orderDetailSave(orderDetailSaveDto);
                }
            }
        }else{
            orderSaveDto.setMember_IDX((int)session.getAttribute("member_IDX"));
            orderIdx = orderService.orderSave(orderSaveDto);
            for(int i=0; i < item_QTY.length; i++){
                orderDetailSaveDto.setItem_IDX(item_IDX[i]);
                orderDetailSaveDto.setOdetail_QTY(item_QTY[i]);
                orderDetailSaveDto.setOdetail_STATUS("기본");
                orderDetailSaveDto.setOrdersIDX(orderIdx);
                productService.plusSell(item_IDX[i], item_QTY[i]);
                orderService.orderDetailSave(orderDetailSaveDto);
            }
            MemberResponseDTO memberResponseDTO = memberService.findByIDX(((int)session.getAttribute("member_IDX")));
            int mileage = orderSaveDto.getAccumulate_MILEAGE() - orderSaveDto.getUsing_MILEAGE();
            memberResponseDTO.setMember_POINT(memberResponseDTO.getMember_POINT() + mileage);
            memberService.changeMemberInfo(memberResponseDTO);
        }
        OrderResponseDto dto = orderService2.findOrderDto(orderIdx);
        model.addAttribute("order",dto);
        return "/client/order/order-complete";
    }
}
