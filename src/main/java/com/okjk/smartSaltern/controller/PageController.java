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

	@GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }

	@GetMapping("/main")
    public String main(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails != null) {
            User user = userDetails.getUser();
            if (user != null) {
                model.addAttribute("loginUser", user);
            } else {
                // user가 null인 경우 로깅 추가
                System.out.println("User is null");
                return "redirect:/login"; // 로그인 페이지로 리다이렉트
            }
        } else {
            // userDetails가 null인 경우 로깅 추가
            System.out.println("UserDetails is null");
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }
        return "main";
    }

    @GetMapping("/join")
    public String join() {
        return "join"; // templates/join.html
    }
    

    
    @GetMapping("/alert")
    public String alert() {
        return "alert"; 
    }
    
    @GetMapping("/message")
    public String message() {
        return "message"; 
    }
    
    @GetMapping("/join_multi")
    public String join_multi() {
        return "join_multi"; 
    }
    
    @GetMapping("/join_multi_1")
    public String join_multi_1() {
        return "join_multi_1"; 
    }
    
    @GetMapping("/join_multi_2")
    public String join_multi_2() {
        return "join_multi_2"; 
    }
    
    @GetMapping("/join_multi_3")
    public String join_multi_3() {
        return "join_multi_3"; 
    }
    
    @GetMapping("/profile")
    public String profile() {
        return "profile"; 
    }
    
    @GetMapping("/security")
    public String security() {
        return "security"; 
    }
    
    @GetMapping("/password_find")
    public String password_find() {
        return "password_find"; 
    }
    
    @GetMapping("/notifications")
    public String notifications() {
        return "notifications"; 
    }
    
    @GetMapping("/report_list")
    public String report_list() {
        return "report_list"; 
    }
    
    @GetMapping("/report_create")
    public String report_create() {
        return "report_create"; 
    } 
    
    @GetMapping("/report_edit")
    public String report_edit() {
        return "report_edit"; 
    }
    
    @GetMapping("/request_demo")
    public String request_demo() {
        return "request_demo"; 
    }
    
    @GetMapping("/header")
    public String header() {
        return "header"; 
    }
    
    @GetMapping("/sensor")
    public String sensor() {
        return "sensor"; 
    }
    
    @GetMapping("/equipment")
    public String equipment() {
        return "equipment"; 
    }
    
    
    @GetMapping("/salt_farm")
    public String salt_farm() {
        return "salt_farm"; 
    }
    
    

    

    // 로그아웃은 Security에서 처리하므로 따로 컨트롤러 필요 없음
}

