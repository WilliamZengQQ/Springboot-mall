package com.williamzeng.springbootmall.service.impl;

import com.williamzeng.springbootmall.dao.ProductDao;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    //將productDao註解注入進來
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }
}
