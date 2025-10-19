package com.hooper.ugg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hooper.ugg.ugg_enum.ResponseCode;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.WorkSchedule;
import com.hooper.ugg.mapper.WorkScheduleMapper;
import com.hooper.ugg.service.IWorkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hooper
 * @since 2025-10-15
 */
@Service
public class WorkScheduleServiceImpl extends ServiceImpl<WorkScheduleMapper, WorkSchedule>
        implements IWorkScheduleService {

    @Autowired
    private WorkScheduleMapper workScheduleMapper;

    @Override
    public Result<?> addSchedule(WorkSchedule schedule) {
        int insert = workScheduleMapper.insert(schedule);
        if (insert != 1) {
            return Result.fail(ResponseCode.INSERT_FAILED);
        }
        return Result.success(schedule);
    }

    @Override
    public Result<List<WorkSchedule>> getAllSchedules() {
        List<WorkSchedule> list = workScheduleMapper.selectList(null);
        return Result.success(list);
    }

    @Override
    public Result<List<WorkSchedule>> getSchedulesByStaffId(String staffId) {
        LambdaQueryWrapper<WorkSchedule> query = Wrappers.lambdaQuery(WorkSchedule.class)
                .eq(WorkSchedule::getStaffId, staffId);
        List<WorkSchedule> list = workScheduleMapper.selectList(query);
        return Result.success(list);
    }

    @Override
    public Result<?> updateSchedule(WorkSchedule schedule) {
        if (StringUtils.isBlank(schedule.getWorkScheduleId())) {
            return Result.fail("schedule ID must not be blank");
        }
        int update = workScheduleMapper.updateById(schedule);
        if (update != 1) {
            return Result.fail(ResponseCode.UPDATE_FAILED);
        }
        return Result.success();
    }

    @Override
    public Result<?> deleteSchedule(String workScheduleId) {
        int delete = workScheduleMapper.deleteById(workScheduleId);
        if (delete != 1) {
            return Result.fail(ResponseCode.DELETE_FAILED);
        }
        return Result.success();
    }
}
