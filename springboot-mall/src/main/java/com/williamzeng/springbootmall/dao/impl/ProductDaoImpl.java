package com.williamzeng.springbootmall.dao.impl;

import com.williamzeng.springbootmall.dao.ProductDao;
import com.williamzeng.springbootmall.dto.ProdustRequest;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component //將ProductDaoImpl轉會為一個Bean
public class ProductDaoImpl implements ProductDao {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate; //將NamedParameterJdbcTemplate注入進來引用這個類當中的function

    @Override
    public Product getProductById(Integer productId){
        String sql = "SELECT product_id, product_name, " +
                "category, image_url, price, stock, description, created_date,  + last_modified_date FROM product WHERE product_id = :productId";


        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());//query要傳入3個數據。
                                                                                                // 第三個參數是RowMap是要將MySQL所查詢的數據轉換成JAVA Object

        if (productList.size() > 0 ){
            return productList.get(0);
        }
        else {
            return null;
        }

    }

    @Override
    public Integer createProduct(ProdustRequest productRequest){
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description)"+
                ":createdDate, :lastModifiedDate";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        return keyHolder.getKey().intValue();
    }



}
