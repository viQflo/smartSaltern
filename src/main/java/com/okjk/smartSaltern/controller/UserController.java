package com.okjk.smartSaltern.controller;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/auth-login-basic.html";
    }
}
