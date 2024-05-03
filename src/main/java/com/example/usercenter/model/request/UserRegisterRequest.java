package com.example.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author bcb
 */
@Data
public class UserRegisterRequest{

    private String userAccount;

    private String userPassword;

    private String checkPassword;
}
