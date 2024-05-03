package com.example.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.ErrorCode;
import com.example.usercenter.common.ResultUtils;
import com.example.usercenter.constant.UserConstant;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.model.User;
import com.example.usercenter.model.request.UserLoginRequest;
import com.example.usercenter.model.request.UserRegisterRequest;
import com.example.usercenter.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author bcb
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        //return new BaseResponse<>(0,user,"ok");
        return ResultUtils.success(user);
    }

    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return userService.userLogout(request);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        //鉴权，仅管理员可查询
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUsers(@RequestBody int id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        if (id <= 0) {
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null){
            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
        }
        long userId = currentUser.getId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        //鉴权，仅管理员可删除
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        if (user.getUserRole() != UserConstant.ADMIN_ROLE || user == null) {
            return false;
        }
        return true;
    }
}
