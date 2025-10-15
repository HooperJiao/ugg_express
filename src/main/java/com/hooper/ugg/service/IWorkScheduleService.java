package com.hooper.ugg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.WorkSchedule;

import java.util.List;

/**
 * @author Hooper
 * @since 2025-10-15
 */
public interface IWorkScheduleService extends IService<WorkSchedule> {

    /**
     * 添加工作排班信息
     * @param schedule 排班信息
     * @return 操作结果
     */
    Result<?> addSchedule(WorkSchedule schedule);

    /**
     * 查询所有排班（可扩展后续分页或条件筛选）
     * @return 排班列表
     */
    Result<List<WorkSchedule>> getAllSchedules();

    /**
     * 根据 staffId 查询该员工所有排班信息
     * @param staffId 员工ID
     * @return 排班列表
     */
    Result<List<WorkSchedule>> getSchedulesByStaffId(String staffId);

    /**
     * 修改排班信息
     * @param schedule 包含排班ID的更新对象
     * @return 是否更新成功
     */
    Result<?> updateSchedule(WorkSchedule schedule);

    /**
     * 删除排班记录（物理删除）
     * @param workScheduleId 排班主键ID
     * @return 是否删除成功
     */
    Result<?> deleteSchedule(String workScheduleId);
}