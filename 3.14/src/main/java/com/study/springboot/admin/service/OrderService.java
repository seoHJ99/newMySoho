package com.study.springboot.admin.service;


import com.study.springboot.admin.dto.CouponResoponseDTO;
import com.study.springboot.client.dto.OrderDetailTemp;
import com.study.springboot.client.dto.OrdersStatus;
import com.study.springboot.client.dto.ProductInfo;
import com.study.springboot.entity.Order;
import com.study.springboot.entity.OrderDetail;
import com.study.springboot.entity.OrderDetailRepository;
import com.study.springboot.entity.OrdersRepository;
import com.study.springboot.admin.dto.OrderResponseDto;
import com.study.springboot.admin.dto.OrderSaveDto;
import com.study.springboot.admin.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final ProductService productService;
    private final OrderDetailRepository orderDetailRepository;

    @Transactional(readOnly = true)
    public OrderResponseDto findByOrderId(int idx){
        Order order = ordersRepository.findById(idx).get();
        if((""+order.getMember_IDX()).length()!=0) {
            OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                    .orders_DATE(order.getOrdersDATE())
                    .orders_ADDRESS(order.getOrders_ADDRESS())
                    .orders_PHONE(order.getOrders_PHONE())
                    .orders_AMOUNT(order.getOrders_AMOUNT())
                    .orders_DELIVERYPAY(order.getOrders_DELIVERYPAY())
                    .orders_PAYMENT(order.getOrders_PAYMENT())
                    .orders_POST(order.getOrders_POST())
                    .orders_STATUS(order.getOrders_STATUS())
                    .orders_IDX(order.getOrdersIDX())
                    .orders_NAME(order.getOrders_NAME())
                    .nonmember_IDX(order.getNonmember_IDX())
                    .build();
            return orderResponseDto;
        }
        else {OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                        .orders_DATE(order.getOrdersDATE())
                        .orders_ADDRESS(order.getOrders_ADDRESS())
                        .orders_PHONE(order.getOrders_PHONE())
                        .orders_AMOUNT(order.getOrders_AMOUNT())
                        .orders_DELIVERYPAY(order.getOrders_DELIVERYPAY())
                        .orders_PAYMENT(order.getOrders_PAYMENT())
                        .orders_POST(order.getOrders_POST())
                        .orders_STATUS(order.getOrders_STATUS())
                        .orders_IDX(order.getOrdersIDX())
                        .orders_NAME(order.getOrders_NAME())
                .member_IDX(order.getMember_IDX())
                .build();
                return orderResponseDto;

        }
    }
    @Transactional(readOnly = true)
    public List<OrderDetail> findOrderDetailByOrderId(int idx){
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrdersIDX(idx);
        return orderDetailList;
    }
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findOrderItems (List<OrderDetail> orderDetailList) {
        List<ProductResponseDto> list = new ArrayList<>();
        for(OrderDetail orderDetail : orderDetailList){
            ProductResponseDto temp = productService.findById(orderDetail.getItem_IDX());
            list.add(temp);
        }
        return list;
    }

    public OrderResponseDto findOrderDto(int id){
       Order entity = ordersRepository.findById(id).get();
        OrderResponseDto dto = new OrderResponseDto(entity);
        return dto;
    }

    public void saveOrder(OrderSaveDto dto){
        Order entity = dto.toSaveEntity();
        ordersRepository.save(entity);
    }

    public List<OrderResponseDto> findOrderByMemberIDX(int memIDX){
        List<Order> entityList = ordersRepository.findOrdersByMemberIDX(memIDX);
        List<OrderResponseDto> list = new ArrayList<>();
        for(Order entity : entityList){
            OrderResponseDto dto = new OrderResponseDto(entity);
            list.add(dto);
        }
        return list;
    }

    public List<OrderDetailTemp> userMyOrderLogic(List<OrderResponseDto> dtoList, List<CouponResoponseDTO> couponList) {
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
            orderDetailTemp.setMember_IDX(dtoList.get(j).getMember_IDX());


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
                productInfo.setItem_IDX(orderDetailList.get(i).getItem_IDX());
                productInfo.setItem_NAME(productList.get(i).getItem_NAME());
                productInfo.setItem_IMAGE(productList.get(i).getItem_IMAGE());
                productInfo.setItem_PRICE(productList.get(i).getItem_PRICE());
                productInfo.setQty(orderDetailList.get(i).getOdetail_QTY());
                productInfo.setItem_OPTION(productList.get(i).getItem_OPTION());
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
    public OrdersStatus dtoListLogic(List<OrderResponseDto> dtoList, List<OrderDetailTemp> orderTests) {
        OrdersStatus ordersStatusList = new OrdersStatus();

        int noPayCnt = 0;
        int orderReadyCnt = 0;
        int orderingCnt = 0;
        int completeCnt = 0;

        for(int i = 0; i < dtoList.size(); i++) {
            String status = dtoList.get(i).getOrders_STATUS();
            switch (status) {
                case "입금대기" : noPayCnt++;
                    break;
                case "배송준비" : orderReadyCnt++;
                    break;
                case "배송중" : orderingCnt++;
                    break;
                case "배송완료" : completeCnt++;
                    break;
            }
        }

        ordersStatusList.setNoPayCnt(noPayCnt);
        ordersStatusList.setOrderReadyCnt(orderReadyCnt);
        ordersStatusList.setOrderingCnt(orderingCnt);
        ordersStatusList.setCompleteCnt(completeCnt);

        return ordersStatusList;
    }

}
