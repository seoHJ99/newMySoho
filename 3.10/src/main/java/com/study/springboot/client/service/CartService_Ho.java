package com.study.springboot.client.service;

import com.study.springboot.admin.dto.OrderSaveDto;
import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.service.OrderService;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.client.dto.CartInformation;
import com.study.springboot.client.dto.NonmemberSaveDto;
import com.study.springboot.entity.CartRepository;
import com.study.springboot.entity.NonmemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CartService_Ho {
    private final ProductService productService;
    private final CL_OrderService orderService;

    @Transactional(readOnly = true)
    public List<CartInformation> makeCartDto(int item_list[], int item_quantity[]){
        List<CartInformation> cartDtoList = new ArrayList<>();
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
        return cartDtoList;
    }

    public OrderSaveDto setCartInfoToOrderDto(OrderSaveDto orderSaveDto, HttpServletRequest request){
        orderSaveDto.setOrders_PHONE(request.getParameter("phone1")+request.getParameter("phone2"));
        orderSaveDto.setSenders_PHONE(request.getParameter("sender_phone1")+request.getParameter("sender_phone2"));
        orderSaveDto.setOrders_ADDRESS(
                request.getParameter("delivery_address1")
                        +","+request.getParameter("delivery_address2")
                        +","+request.getParameter("delivery_address3"));
        if(orderSaveDto.getOrders_PAYMENT().equals("무통장결제")){
            orderSaveDto.setOrders_STATUS("입금대기");
        }else {
            orderSaveDto.setOrders_STATUS("배송준비");
        }
        return orderSaveDto;
    }

    @Transactional
    public void orderDetailSave(int orderIdx, int item_IDX[], int item_QTY[]){
        OrderDetailSaveDto orderDetailSaveDto = new OrderDetailSaveDto();
        for (int i = 0; i < item_QTY.length; i++) {
            orderDetailSaveDto.setOdetail_QTY(item_QTY[i]);
            orderDetailSaveDto.setItem_IDX(item_IDX[i]);
            orderDetailSaveDto.setOdetail_STATUS("기본");
            orderDetailSaveDto.setOrdersIDX(orderIdx);
            productService.plusSell(item_IDX[i], item_QTY[i]);
            orderService.orderDetailSave(orderDetailSaveDto);
        }
    }

    @Transactional
    public int saveNonmember(OrderSaveDto orderSaveDto){
        int nonmemberIdx = orderService.saveNonmember(orderSaveDto.getOrders_NAME(), orderSaveDto.getOrders_PHONE());
        return nonmemberIdx;
    }
}
