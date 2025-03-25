package com.williamzeng.springbootmall.controller;

import com.williamzeng.springbootmall.dto.CreateOrderRequest;
import com.williamzeng.springbootmall.model.Order;
import com.williamzeng.springbootmall.dto.OrderQueryParams;
import com.williamzeng.springbootmall.service.OrderService;
import com.williamzeng.springbootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/users/{userId}/orders")
    public ResponseEntity<Page<Order>> getOrders(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "10") @Max(1000) @Min(10) Integer limit,
            @RequestParam(defaultValue = "0") @Min(0) Integer offset
    ){
        OrderQueryParams orderQueryParams = new OrderQueryParams();
        orderQueryParams.setLimit(limit);
        orderQueryParams.setOffset(offset);
        orderQueryParams.setUserId(userId);

        //取得 Order list
        List<Order> orderList = orderService.getOrder(orderQueryParams);

        //取得 Order 總數
        Integer count = orderService.countOrder(orderQueryParams);

        //分頁
        Page<Order> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(count);
        page.setResult(orderList);

        return ResponseEntity.status(HttpStatus.OK).body(page);


    }

    @PostMapping("users/{userId}/orders") //這裡的url為商用邏輯，每一個人購買東西，必是以帳號已經存在下的情況進行操作。
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest)
    {
        Integer orderId =  orderService.createOrder(userId,createOrderRequest);

        Order order = orderService.getOrderById(orderId); //嘗試使用orderId將database的訂單資料提取出來

        return ResponseEntity.status(HttpStatus.OK).body(order);
    }
}
