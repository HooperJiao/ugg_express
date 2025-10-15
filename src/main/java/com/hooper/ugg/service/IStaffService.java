package com.hooper.ugg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.Staff;

/**
 * 员工服务接口
 * 用于员工信息的增删改查及状态管理
 *
 * @author Hooper
 * @since 2025-10-15
 */
public interface IStaffService extends IService<Staff> {

    /**
     * 添加新员工
     *
     * @param staff 员工信息（staff_id 可为空，自动生成）
     * @return 操作结果（成功或失败）
     */
    Result<?> addStaff(Staff staff);

    /**
     * 查询所有状态为正常的员工（staff_status != 0）
     *
     * @return 查询结果（包含员工列表或失败信息）
     */
    Result<?> getAllActiveStaff();

    /**
     * 按条件查询员工，自动拼接非空字段作为筛选条件
     *
     * @param condition 查询条件对象（可为空字段）
     * @return 查询结果（包含员工列表或失败信息）
     */
    Result<?> queryStaffByCondition(Staff condition);

    /**
     * 更新员工信息
     *
     * @param staff 包含 staff_id 和待修改字段的员工对象
     * @return 操作结果（成功或失败）
     */
    Result<?> updateStaff(Staff staff);

    /**
     * 注销员工（逻辑删除，将 staff_status 设置为 0）
     *
     * @param staffId 员工ID
     * @return 操作结果（成功或失败）
     */
    Result<?> deactivateStaff(String staffId);

    /**
     * 删除员工（物理删除，彻底清除记录）
     *
     * @param staffId 员工ID
     * @return 操作结果（成功或失败）
     */
    Result<?> deleteStaff(String staffId);
}