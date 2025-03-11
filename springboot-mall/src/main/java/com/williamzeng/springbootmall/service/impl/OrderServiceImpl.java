package com.williamzeng.springbootmall.service.impl;

import com.williamzeng.springbootmall.dao.OrderDao;
import com.williamzeng.springbootmall.dao.ProductDao;
import com.williamzeng.springbootmall.dto.ByItem;
import com.williamzeng.springbootmall.dto.CreateOrderRequest;
import com.williamzeng.springbootmall.model.Order;
import com.williamzeng.springbootmall.model.OrderItem;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest){

        //計算總共使用者消費資訊
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        /*
        createOrderRequest.getByItemList()：回傳一個 集合（如 List、Set）或陣列。
        ByItem byItem：表示 從集合中取出的單個元素，類型是 ByItem。
        :（冒號）可以解釋為 「in」，即「for each byItem in createOrderRequest.getByItemList()」。
        迴圈每次執行時，byItem 會指向 getByItemList() 中的下一個元素，直到遍歷完整個集合。
         */
        for(ByItem byItem : createOrderRequest.getByItemList()){
            Product product = productDao.getProductById(byItem.getProductId());

            //計算總價格
            int amount = byItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount+amount;

            //轉換ByItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(byItem.getProductId());
            orderItem.setQuantity(byItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId,totalAmount);
        orderDao.createOrderItems(orderId,orderItemList);

        return orderId;
    }
}
