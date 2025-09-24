package com.hooper.ugg.service.impl;

import com.hooper.ugg.entity.User;
import com.hooper.ugg.mapper.UserMapper;
import com.hooper.ugg.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hooper
 * @since 2025-09-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
