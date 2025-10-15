package com.hooper.ugg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hooper
 * @since 2025-09-25
 */
public interface IUserService extends IService<User> {

    /**
     * 添加新用户
     *
     * @param user 用户信息（userId 可空，自动生成UUID）
     * @return 操作结果
     */
    Result<?> addUser(User user);

    /**
     * 查询所有未注销正常的用户（user_status != 0）
     *
     * @return 正常用户列表
     */
    List<User> getAllActiveUsers();

    /**
     * 按条件查询用户（自动拼接）
     *
     * @param condition 查询条件可选
     * @return 匹配用户列表
     */
    List<User> queryUsersByCondition(User condition);

    /**
     * 更新用户信息
     *
     * @param user 包含 userId 和待修改字段的用户对象
     * @return 是否更新成功
     */
    Result<?> updateUser(User user);

    /**
     * 注销用户（逻辑删除，设置 user_status = 0）
     *
     * @param userId 用户ID
     * @return 是否注销成功
     */
    Result<?> deactivateUser(String userId);

    /**
     * 删除用户（物理删除，彻底删除记录）
     *
     * @param userId 用户ID
     * @return 是否删除成功
     */
    Result<?> deleteUser(String userId);
}
