package com.williamzeng.springbootmall.service;

import com.williamzeng.springbootmall.dto.CreateOrderRequest;
import com.williamzeng.springbootmall.dto.OrderQueryParams;
import com.williamzeng.springbootmall.model.Order;
import com.williamzeng.springbootmall.model.Product;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
    List<Order> getOrder(OrderQueryParams orderQueryParams);
    Integer countOrder(OrderQueryParams orderQueryParams);
}
