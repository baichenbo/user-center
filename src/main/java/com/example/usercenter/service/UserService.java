package com.example.usercenter.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 * @author Bai Bai
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 返回脱敏后的用户信息
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 请求用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}

