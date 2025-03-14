package com.williamzeng.springbootmall.service;

import com.williamzeng.springbootmall.dto.CreateOrderRequest;
import com.williamzeng.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
}
