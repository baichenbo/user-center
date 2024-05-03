package com.example.usercenter.service;

import java.util.Date;

import com.example.usercenter.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("dogBai");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("123456");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
    }

    @Test
    void useRegister() {
        String userAccount = "bcbc";
        String password = "123456789";
        String checkPassword = "123456789";
        long result = userService.userRegister(userAccount, password, checkPassword);
//        Assertions.assertEquals(-1, result);
//        userAccount = "bc";
//        result = userService.useRegister(userAccount, password, checkPassword);
//        Assertions.assertEquals(-1, result);
//        userAccount = "bcbc";
//        password = "123456";
//        result = userService.useRegister(userAccount, password, checkPassword);
        System.out.println("result: " + result);
    }

}