package com.okjk.smartSaltern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
    	 System.out.println("userId: " + user.getUserId()); // 👈 null 나올 수 있음
    	    System.out.println("userPw: " + user.getUserPw());
    	    System.out.println("userDepartment: " + user.getUserDepartment());
        userService.register(user);
        return "redirect:/login";
    }
}
