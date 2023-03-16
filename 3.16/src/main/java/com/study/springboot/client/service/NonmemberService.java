package com.study.springboot.client.service;

import com.study.springboot.admin.dto.OrderResponseDto;
import com.study.springboot.admin.dto.ProductResponseDto;
import com.study.springboot.admin.service.ProductService;
import com.study.springboot.client.dto.NonmemberResponseDto;
import com.study.springboot.client.dto.OrderDetailTemp;
import com.study.springboot.client.dto.ProductInfo;
import com.study.springboot.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
//made by 형민
public class NonmemberService {

    private final NonmemberRepository nonmemberRepository;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;
    private final OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    public NonmemberResponseDto findNonmember(String name, String phone) {
        Nonmember nonmemdb = nonmemberRepository.findNonmemberBynameAndPhone(name, phone);
        if (nonmemdb == null) {
            return null;
        } else {
            NonmemberResponseDto nondto = new NonmemberResponseDto(nonmemdb);
            return nondto;
        }
    }
        //
    public List<OrderResponseDto> findOrderByNonMemberIDX(int nonMemIDX) {
        List<Order> entityList = ordersRepository.findOrdersByNonMemberIDX(nonMemIDX);

        List<OrderResponseDto> list = new ArrayList<>();
        for (Order entity : entityList) {
            OrderResponseDto dto = new OrderResponseDto(entity);
            list.add(dto);
        }
        return list;
    }


    public List<OrderDetailTemp> userMyOrderLogic(List<OrderResponseDto> dtoList) {
        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<ProductResponseDto> productList = new ArrayList<>();
        List<OrderDetailTemp> orderTests = new ArrayList<>();
        for (int j = 0; j < dtoList.size(); j++) {
            orderDetailList = findOrderDetailByOrderId(dtoList.get(j).getOrders_IDX()); // 주문 상세. 해당 주문에서 어떤 상품을 주문했는지, 몇개나 샀는지 알수 있다.
            OrderDetailTemp orderDetailTemp = new OrderDetailTemp();
            orderDetailTemp.setOrders_DATE(dtoList.get(j).getOrders_DATE());
            orderDetailTemp.setOrders_IDX(dtoList.get(j).getOrders_IDX());
            orderDetailTemp.setOrders_NAME(dtoList.get(j).getOrders_NAME());

            orderDetailTemp.setOrders_PHONE(dtoList.get(j).getOrders_PHONE());
            orderDetailTemp.setOrders_ADDRESS(dtoList.get(j).getOrders_ADDRESS());
            orderDetailTemp.setOrders_PAYMENT(dtoList.get(j).getOrders_PAYMENT());
            orderDetailTemp.setSenders_NAME(dtoList.get(j).getSenders_NAME());
            orderDetailTemp.setSenders_PHONE(dtoList.get(j).getSenders_PHONE());
            orderDetailTemp.setOrders_DELIVERYPAY(dtoList.get(j).getOrders_DELIVERYPAY());
            orderDetailTemp.setOrders_STATUS(dtoList.get(j).getOrders_STATUS());
            orderDetailTemp.setUsing_MILEAGE(dtoList.get(j).getUsing_MILEAGE());


            int priceTemp = 0;
            int discountPrice = 0;
            int refundPrice = 0;
            int refundCnt = 0;
            int mileage = dtoList.get(j).getUsing_MILEAGE();

            List<ProductInfo> proTestList = new ArrayList<>();
            for (int i = 0; i < orderDetailList.size(); i++) {
                productList = findOrderItems(orderDetailList);
                ProductInfo productInfo = new ProductInfo();
                productInfo.setStatus(orderDetailList.get(i).getOdetail_STATUS());

                productInfo.setItem_NAME(productList.get(i).getItem_NAME());
                productInfo.setItem_IMAGE(productList.get(i).getItem_IMAGE());
                productInfo.setItem_PRICE(productList.get(i).getItem_PRICE());
                productInfo.setQty(orderDetailList.get(i).getOdetail_QTY());
                int price = productList.get(i).getItem_PRICE();
                priceTemp = priceTemp + (price * productInfo.getQty());
                int discount = productList.get(i).getItem_DISCOUNT();
                discountPrice = ((price - ((int) (Math.floor((price - (price * discount / 100)) / 100) * 100)))* productInfo.getQty()) + discountPrice;
                productInfo.setItem_DISCOUNT(discount);
                productInfo.setItem_PRICE_DISCOUNT((int) (Math.floor((price - (price * discount / 100)) / 100) * 100));
                productInfo.setItem_FULL_PRICE(productInfo.getItem_PRICE_DISCOUNT()*productInfo.getQty());

                if(productInfo.getItem_DISCOUNT() > 0 && productInfo.getStatus().equals("환불")) {
                    refundPrice = refundPrice + (productInfo.getItem_PRICE_DISCOUNT()*productInfo.getQty());
                } else if(productInfo.getItem_DISCOUNT() == 0 && productInfo.getStatus().equals("환불")) {
                    refundPrice = refundPrice + (productInfo.getItem_PRICE()*productInfo.getQty());
                }

                if(productInfo.getStatus().equals("환불") || productInfo.getStatus().equals("교환")) {
                    refundCnt++;
                }

                proTestList.add(productInfo);
                orderDetailTemp.setProductInfoList(proTestList);
            }

            orderDetailTemp.setRefundCnt(refundCnt);

            orderDetailTemp.setRefund_PRICE(refundPrice);
            orderDetailTemp.setItem_PRICE(priceTemp);
            orderDetailTemp.setItem_DISCOUNT(discountPrice);
            switch (orderDetailTemp.getOrders_DELIVERYPAY()) {
                case "무료":
                    orderDetailTemp.setItem_LAST_PRICE(priceTemp - discountPrice - mileage);
                    break;
                case "일반":
                    orderDetailTemp.setItem_LAST_PRICE(priceTemp - discountPrice - mileage + 3000);
                    break;
                case "특수":
                    orderDetailTemp.setItem_LAST_PRICE(priceTemp - discountPrice - mileage + 5000);
                    break;
            }

            orderTests.add(orderDetailTemp);
        }
        return orderTests;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findOrderItems(List<OrderDetail> orderDetailList) {
        List<ProductResponseDto> list = new ArrayList<>();
        for (OrderDetail orderDetail : orderDetailList) {
            ProductResponseDto temp = productService.findById(orderDetail.getItem_IDX());
            list.add(temp);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> findOrderDetailByOrderId(int idx) {
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrdersIDX(idx);
        return orderDetailList;
    }


}
