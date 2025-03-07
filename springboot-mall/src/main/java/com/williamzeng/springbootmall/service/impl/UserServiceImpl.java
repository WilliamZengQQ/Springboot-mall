package com.williamzeng.springbootmall.service.impl;

import com.williamzeng.springbootmall.dao.UserDao;
import com.williamzeng.springbootmall.dto.UserLoginRequest;
import com.williamzeng.springbootmall.dto.UserRegisterRequest;
import com.williamzeng.springbootmall.model.User;
import com.williamzeng.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    //定義log
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /*
    那在 Service 層這裡
    之所以會去使用 register 這個名字的原因是在於
    那在這個方法的內部
    其實不單純是去創建帳號
    那他還會多去做一些額外的實作
    譬如說去檢查 email 是否被註冊過之類的
    那所以我才去給他一個 register 的名字
    那表示他是去處理有關註冊相關的所有邏輯
     */
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        //先獲取資料庫當中查詢對應的email的值，判斷有的話，回傳404給前端並停留
        User user = userDao.getByEmail(userRegisterRequest.getEmail());
        if (user != null) {
            //使用log
            /*
            我們在使用log的時候可以去指定log的等級，{}就是後面參數的數值。可以有很多{}但是要對應參數
             */
            logger.warn("該 email{} 已經被註冊", userRegisterRequest.getEmail());
            //噴出ResponseStatusException並回傳狀態碼給前端
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST); //400表示前端的請求是有問題的
        }
        //使用MD5生成密碼的雜湊值(將字串轉換程Bytes類型)
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword); //userRegisterRequest密碼的值就會被轉換為hash計算後的值

        //創建帳號
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {

        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getByEmail(userLoginRequest.getEmail());
        //檢查User是否存在
        if (user == null) {
            logger.warn("該 email{} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //使用MD5生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        /*
        在Java當中比較字串一定要使用.equals()不可以使用==
         */
        //比較密碼（比較雜湊）
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        }
        else {
            logger.warn("該 email{} 密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
