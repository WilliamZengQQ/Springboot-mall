package com.williamzeng.springbootmall.controller;

import com.williamzeng.springbootmall.dto.CreateOrderRequest;
import com.williamzeng.springbootmall.model.Order;
import com.williamzeng.springbootmall.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("users/{userId}/orders") //這裡的url為商用邏輯，每一個人購買東西，必是以帳號已經存在下的情況進行操作。
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest)
    {
        Integer orderId =  orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrdderById(orderId); //嘗試使用orderId將database的訂單資料提取出來

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
