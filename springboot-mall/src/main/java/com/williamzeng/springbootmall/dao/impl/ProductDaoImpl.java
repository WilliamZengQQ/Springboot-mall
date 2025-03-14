package com.williamzeng.springbootmall.dao.impl;

import com.williamzeng.springbootmall.dao.ProductDao;
import com.williamzeng.springbootmall.dto.ProductQueryParams;
import com.williamzeng.springbootmall.dto.ProductRequest;
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
    public List<Product> getProducts(ProductQueryParams productQueryParams){
        String sql = "SELECT product_id, product_name, " +
                "category, image_url, price, stock, description, created_date,  + last_modified_date FROM product WHERE 1=1";
        Map<String, Object> map = new HashMap<>();

        sql=addFilteringSql(sql,map,productQueryParams);

        //排序
        sql += " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();
        //在使用ORDER BY的語法後面只能用拼接到方是進行sql宣告

        //分頁
        sql +=  " LIMIT :limit OFFSET :offset";
        map.put("limit",productQueryParams.getLimit());
        map.put("offset",productQueryParams.getOffset());

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());
        return productList;
    }

    @Override
    public Integer countProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT count(*) FROM product WHERE 1=1"; //取得product table當中商品數量
        Map<String, Object> map = new HashMap<>();
        sql=addFilteringSql(sql,map,productQueryParams);

        Integer total = namedParameterJdbcTemplate.queryForObject(sql, map, Integer.class);
        return total;

    }

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
    public Integer createProduct(ProductRequest productRequest){
        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, description,created_date,last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description,:created_date, :last_modified_date)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());

        map.put("category", productRequest.getCategory().name());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("created_date", now);
        map.put("last_modified_date", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder); //這邊掛掉

        int productId = keyHolder.getKey().intValue();
        return productId;
    }

    @Override
    public void updatedProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET " +
                "product_name = :productName, " +
                "category = :category, " +
                "image_url = :imageUrl, " +
                "price = :price, " +
                "stock = :stock, " +
                "description = :description, " +
                "last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productId", productId);
        paramMap.put("productName", productRequest.getProductName());
        paramMap.put("category", productRequest.getCategory().name());
        paramMap.put("imageUrl", productRequest.getImageUrl());
        paramMap.put("price", productRequest.getPrice());
        paramMap.put("stock", productRequest.getStock());
        paramMap.put("description", productRequest.getDescription());
        paramMap.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void updateStock(Integer productId, Integer stock) {
        String sql = "UPDATE product SET stock = :stock, last_modified_date = :lastModifiedDate" +
                " WHERE product_id = :productId";

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("productId",productId);
        paramMap.put("stock",stock);
        paramMap.put("lastModifiedDate",new Date());
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void deleteProductById(Integer productId){
        String sql = "DELETE FROM product WHERE product_id = :productId";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("productId", productId);
        namedParameterJdbcTemplate.update(sql, paramMap);
    }

    //提煉程式（軟體的價值在於重複使用）
    private String addFilteringSql(String sql,Map<String, Object> map,ProductQueryParams productQueryParams) {
        //查詢條件
        if ( productQueryParams.getProductCategory()!= null) {
            sql += " AND category = :category"; //sql select 寫上 where 1=1一定要注意在AND前頭要加上空白鍵
            map.put("category", productQueryParams.getProductCategory().name());
        }

        if (productQueryParams.getSearch() != null) {
            sql += " AND product_name LIKE :search";
            map.put("search", "%" + productQueryParams.getSearch() + "%"); //在SQL當中模糊查詢
        }
        return sql;
    }



}
