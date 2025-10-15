package com.hooper.ugg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hooper.ugg.common.ResponseCode;
import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.Staff;
import com.hooper.ugg.mapper.StaffMapper;
import com.hooper.ugg.service.IStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImpl extends ServiceImpl<StaffMapper, Staff> implements IStaffService {

    @Autowired
    private StaffMapper staffMapper;

    @Override
    public Result<?> addStaff(Staff staff) {
        int insert = staffMapper.insert(staff);
        if (insert != 1) {
            return Result.fail(ResponseCode.INSERT_FAILED);
        }
        return Result.success(staff);
    }

    @Override
    public Result<?> getAllActiveStaff() {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        wrapper.ne("staff_status", 0);
        List<Staff> list = staffMapper.selectList(wrapper);
        return Result.success(list);
    }

    @Override
    public Result<?> queryStaffByCondition(Staff condition) {
        QueryWrapper<Staff> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(condition.getStaffId())) {
            wrapper.eq("staff_id", condition.getStaffId());
        }
        if (StringUtils.isNotBlank(condition.getUserId())) {
            wrapper.eq("user_id", condition.getUserId());
        }
        if (StringUtils.isNotBlank(condition.getRealName())) {
            wrapper.like("real_name", condition.getRealName());
        }
        if (condition.getStaffStatus() != null) {
            wrapper.eq("staff_status", condition.getStaffStatus());
        } else {
            wrapper.ne("staff_status", 0);
        }
        List<Staff> list = staffMapper.selectList(wrapper);
        return Result.success(list);
    }

    @Override
    public Result<?> updateStaff(Staff staff) {
        if (StringUtils.isBlank(staff.getStaffId())) {
            return Result.fail("Staff ID must not be blank");
        }
        int updated = staffMapper.updateById(staff);
        if (updated != 1) {
            return Result.fail(ResponseCode.UPDATE_FAILED);
        }
        return Result.success();
    }

    @Override
    public Result<?> deactivateStaff(String staffId) {
        if (StringUtils.isBlank(staffId)) {
            return Result.fail("Staff ID must not be blank");
        }
        Staff staff = new Staff();
        staff.setStaffId(staffId);
        staff.setStaffStatus(0);  // 0 = 注销
        int update = staffMapper.updateById(staff);
        if (update != 1) {
            return Result.fail(ResponseCode.UPDATE_FAILED);
        }
        return Result.success();
    }

    @Override
    public Result<?> deleteStaff(String staffId) {
        if (StringUtils.isBlank(staffId)) {
            return Result.fail("Staff ID must not be blank");
        }
        int delete = staffMapper.deleteById(staffId);
        if (delete != 1) {
            return Result.fail(ResponseCode.DELETE_FAILED);
        }
        return Result.success();
    }
}