package com.hooper.ugg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.common.ResponseCode;
import com.hooper.ugg.entity.User;
import com.hooper.ugg.mapper.UserMapper;
import com.hooper.ugg.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户
     */
    @Override
    public Result<?> addUser(User user) {
        int insert = userMapper.insert(user);
        if (insert != 1) {
            return Result.fail(ResponseCode.INSERT_FAILED);
        }
        return Result.success(user);
    }

    /**
     * 获取所有状态为正常的用户
     */
    @Override
    public List<User> getAllActiveUsers() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.ne("user_status", 0);
        return userMapper.selectList(wrapper);
    }

    /**
     * 根据条件动态查询用户（非空字段作为条件）
     */
    @Override
    public List<User> queryUsersByCondition(User condition) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (StringUtils.hasText(condition.getUserId())) {
            wrapper.eq("user_id", condition.getUserId());
        }
        if (StringUtils.hasText(condition.getUserName())) {
            wrapper.like("user_name", condition.getUserName());
        }
        if (StringUtils.hasText(condition.getRealName())) {
            wrapper.like("real_name", condition.getRealName());
        }
        if (StringUtils.hasText(condition.getUserPhone())) {
            wrapper.eq("user_phone", condition.getUserPhone());
        }
        if (StringUtils.hasText(condition.getUserEmail())) {
            wrapper.eq("user_email", condition.getUserEmail());
        }
        if (condition.getRoleNo() != null) {
            wrapper.eq("role_no", condition.getRoleNo());
        }
        if (condition.getUserStatus() != null) {
            wrapper.eq("user_status", condition.getUserStatus());
        }
        return userMapper.selectList(wrapper);
    }

    /**
     * 更新用户信息
     */
    @Override
    public Result<?> updateUser(User user) {
        if (!StringUtils.hasText(user.getUserId())) {
            return Result.fail(ResponseCode.PARAM_ERROR);
        }
        int update = userMapper.updateById(user);
        if (update != 1) {
            return Result.fail(ResponseCode.UPDATE_FAILED);
        }
        return Result.success();
    }

    /**
     * 注销用户（逻辑删除，将 user_status 设为 0）
     */
    @Override
    public Result<?> deactivateUser(String userId) {
        if (!StringUtils.hasText(userId)) {
            return Result.fail(ResponseCode.PARAM_ERROR);
        }
        User user = new User();
        user.setUserId(userId);
        user.setUserStatus(0);
        int update = userMapper.updateById(user);
        if (update != 1) {
            return Result.fail(ResponseCode.UPDATE_FAILED);
        }
        return Result.success();
    }

    /**
     * 删除用户（物理删除）
     */
    @Override
    public Result<?> deleteUser(String userId) {
        if (!StringUtils.hasText(userId)) {
            return Result.fail(ResponseCode.PARAM_ERROR);
        }
        int delete = userMapper.deleteById(userId);
        if (delete != 1) {
            return Result.fail(ResponseCode.DELETE_FAILED);
        }
        return Result.success();
    }
}