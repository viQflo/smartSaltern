package com.okjk.smartSaltern.controller;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.security.CustomUserDetails;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }

    @RequestMapping("/main")
    public String main(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            if (user != null) {
                model.addAttribute("loginUser", user);
            } else {
                // user가 null인 경우, 예외 처리 혹은 로그인 페이지로 리다이렉트
                return "redirect:/login";
            }
        } else {
            // userDetails가 null인 경우, 로그인 페이지로 리다이렉트
            return "redirect:/login";
        }
        return "main";
    }

    @GetMapping("/join")
    public String join() {
        return "join"; // templates/join.html
    }

    
    @RequestMapping("/alert")
    public String alert() {
        return "alert"; 
    }
    
    @RequestMapping("/message")
    public String message() {
        return "message"; 
    }
    
    @RequestMapping("/join_multi")
    public String join_multi() {
        return "join_multi"; 
    }
    
    @RequestMapping("/join_multi_1")
    public String join_multi_1() {
        return "join_multi_1"; 
    }
    
    @RequestMapping("/join_multi_2")
    public String join_multi_2() {
        return "join_multi_2"; 
    }
    
    @RequestMapping("/join_multi_3")
    public String join_multi_3() {
        return "join_multi_3"; 
    }
    
    @RequestMapping("/profile")
    public String profile() {
        return "profile"; 
    }
    
    @RequestMapping("/security")
    public String security() {
        return "security"; 
    }
    
    @RequestMapping("/password_find")
    public String password_find() {
        return "password_find"; 
    }
    
    

    // 로그아웃은 Security에서 처리하므로 따로 컨트롤러 필요 없음
}

