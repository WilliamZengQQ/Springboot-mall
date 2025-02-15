package com.williamzeng.springbootmall.dao.impl;

import com.williamzeng.springbootmall.dao.ProductDao;
import com.williamzeng.springbootmall.model.Product;
import com.williamzeng.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

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

}
