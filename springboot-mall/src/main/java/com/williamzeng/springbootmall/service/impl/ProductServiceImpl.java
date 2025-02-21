package com.williamzeng.springbootmall.service.impl;

import com.williamzeng.springbootmall.constant.ProductCategory;
import com.williamzeng.springbootmall.dao.ProductDao;
import com.williamzeng.springbootmall.dto.ProductRequest;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    //將productDao註解注入進來
    @Autowired
    private ProductDao productDao;


    @Override
    public List<Product> getProducts(ProductCategory productCategory,String search){

        return productDao.getProducts(productCategory,search);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProductById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest){
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updatedProduct(Integer productId, ProductRequest productRequest){
        productDao.updatedProduct(productId,productRequest);
    }

    @Override
    public void deleteProductById(Integer productId){
        productDao.deleteProductById(productId);
    }

}
