package com.hooper.ugg.controller;

import com.hooper.ugg.common.Result;
import com.hooper.ugg.entity.User;
import com.hooper.ugg.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Hooper
 * @since 2025-09-25
 */
@Controller
@RequestMapping("/ugg/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/addUser")
    @ResponseBody
    public Result<?> addUser(@RequestParam("user") User user) {
        return userService.addUser(user);
    }

}
