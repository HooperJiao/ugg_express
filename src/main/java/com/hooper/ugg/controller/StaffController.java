package com.hooper.ugg.controller;

import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.Staff;
import com.hooper.ugg.entity.User;
import com.hooper.ugg.service.IStaffService;
import com.hooper.ugg.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hooper
 * @since 2025-10-15
 */
@Controller
@RequestMapping("/ugg/staff")
public class StaffController {

    @Autowired
    private IStaffService staffService;

    @PostMapping("/addStaff")
    @ResponseBody
    public Result<?> addUser(@RequestParam("user") Staff staff) {
        return staffService.addStaff(staff);
    }

}
