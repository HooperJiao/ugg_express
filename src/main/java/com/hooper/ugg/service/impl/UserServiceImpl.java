package com.hooper.ugg.service.impl;

import com.hooper.ugg.common.ResponseCode;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.User;
import com.hooper.ugg.mapper.UserMapper;
import com.hooper.ugg.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hooper
 * @since 2025-09-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<?> addUser(User user) {

        int insert = userMapper.insert(user);

        if (insert != 1) {
            return Result.fail(ResponseCode.INSERT_FAILED);
        }
        return Result.success(user);
    }

}
