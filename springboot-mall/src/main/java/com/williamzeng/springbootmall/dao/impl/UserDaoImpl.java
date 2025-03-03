package com.williamzeng.springbootmall.dao.impl;


import com.williamzeng.springbootmall.dao.UserDao;
import com.williamzeng.springbootmall.dto.UserRegisterRequest;
import com.williamzeng.springbootmall.model.User;
import com.williamzeng.springbootmall.rowmapper.UserRowMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component //將UserDaoImpl變成一個bean好讓其它class可直接使用
public class UserDaoImpl implements UserDao {
    //注入jdbc為了產生出sql的database
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date)"+
                "VALUES(:email, :password, :createDate, :lastModifyDate)";

        Map<String, Object> params = new HashMap<>();
        params.put("email", userRegisterRequest.getEmail());
        params.put("password", userRegisterRequest.getPassword());
        Date createDate = new Date();
        params.put("createDate", createDate);
        params.put("lastModifyDate", createDate);

        /*
        GeneratedKeyHolder 是 Spring JDBC 提供的類，主要用來存儲 SQL INSERT 操作後 自動生成的鍵（Primary Key），通常是 AUTO_INCREMENT 的欄位（例如 id）。
        執行 SQL 更新操作（這裡通常是 INSERT）。
        sql：SQL 語句，可能包含命名參數（例如 :name, :email）。
        new MapSqlParameterSource(params)：將參數對應到 SQL 變數。
        keyHolder：這個參數會接收 資料庫自動生成的鍵值（例如 id）。
         */

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(params),keyHolder);

        int userid = keyHolder.getKey().intValue();

        return userid;
    }

    @Override
    public User getUserById(Integer userId) {

        String sql = "SELECT user_id, email, password, created_date, last_modified_date "+
                "FROM user WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<User> userList = namedParameterJdbcTemplate.query(sql,params,new UserRowMap());

        if (userList.size()>0){
            return userList.get(0);
        }
        else{
            return null;
        }

    }
}
