package com.okjk.smartSaltern.controller;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.security.CustomUserDetails;
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
            model.addAttribute("loginUser", user);
        }
        return "main";
    }

 
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // templates/login.html
    }

    @GetMapping("/join")
    public String join() {
        return "join"; // templates/join.html
    }

    
    @RequestMapping("/header")
    public String header() {
        return "header"; 
    }
    
    @RequestMapping("/footer")
    public String footer() {
        return "footer"; 
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

