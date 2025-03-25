package com.williamzeng.springbootmall.service.impl;

import com.williamzeng.springbootmall.dao.OrderDao;
import com.williamzeng.springbootmall.dao.ProductDao;
import com.williamzeng.springbootmall.dao.UserDao;
import com.williamzeng.springbootmall.dao.impl.UserDaoImpl;
import com.williamzeng.springbootmall.dto.ByItem;
import com.williamzeng.springbootmall.dto.CreateOrderRequest;
import com.williamzeng.springbootmall.dto.OrderQueryParams;
import com.williamzeng.springbootmall.model.Order;
import com.williamzeng.springbootmall.model.OrderItem;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.model.User;
import com.williamzeng.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        //檢查User是否存在
        User user = userDao.getUserById(userId);

        if (user == null){
            log.warn(" 該User_id {} 不存在 ",userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //計算總共使用者消費資訊
        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();
        /*
        createOrderRequest.getByItemList()：回傳一個 集合（如 List、Set）或陣列。
        ByItem byItem：表示 從集合中取出的單個元素，類型是 ByItem。
        :（冒號）可以解釋為 「in」，即「for each byItem in createOrderRequest.getByItemList()」。
        迴圈每次執行時，byItem 會指向 getByItemList() 中的下一個元素，直到遍歷完整個集合。
         */
        for (ByItem byItem : createOrderRequest.getByItemList()) {
            Product product = productDao.getProductById(byItem.getProductId());

            //檢查product是否存在、庫存是否足夠
            if (product == null){
                log.warn("商品{}不存在",byItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            } else if (product.getStock() < byItem.getQuantity()) {

                log.warn("商品{}庫存不足，無法購買。剩餘庫存{}，欲購買數量{}",
                        byItem.getProductId(),product.getStock(),byItem.getQuantity());

                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            //扣除商品庫存
            productDao.updateStock(product.getProductId(),product.getStock()-byItem.getQuantity());

            //計算總價格
            int amount = byItem.getQuantity() * product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換ByItem to OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(byItem.getProductId());
            orderItem.setQuantity(byItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);
        }

        //創建訂單
        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }

    @Override
    public Order getOrderById(Integer orderId) {

        //分別從資料庫當中取得這兩張table的數據
        Order order = orderDao.getOrderById(orderId);
        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);
        //合併上方數據
        order.setOrderItemList(orderItemList);
        return order;
    }

    @Override
    /*
    orderDao.getOrders(orderQueryParams) 透過 orderDao（DAO，資料存取物件）從資料庫中獲取 Order 物件列表。
    OrderQueryParams 是查詢參數，可能包含篩選條件，如訂單日期、使用者 ID 等。
     */
    public List<Order> getOrder(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order:orderList){

            //我們都去取得他的 order items，然後把這個 orderItemList，去放在每一個 order 的底下
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId((order.getOrderId()));
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }
}
