package com.okjk.smartSaltern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.service.UserService;

import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {
	
	@Autowired
	private UserService userService;

    @RequestMapping("/")
    public String home() {
        return "index"; // templates/index.html 파일을 반환
    }
    
    @RequestMapping("/main")
    public String main(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            model.addAttribute("loginUser", user);
        }
        return "main";
    }
    
    @RequestMapping("/header")
    public String header() {
        return "header"; // templates/index.html 파일을 반환
    }
    
    @GetMapping("/login")
    public String login() {
    	return "login";
    }
    
    @PostMapping("/login")
    public String doLogin(@RequestParam String userId,
                          @RequestParam String userPw,
                          HttpSession session,
                          Model model) {
        User user = userService.findByUserId(userId);

        if (user != null && userService.matchPassword(userPw, user.getUserPw())) {
            session.setAttribute("loginUser", user);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("msg", "아이디 또는 비밀번호가 틀렸습니다.");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 날리기
        return "redirect:/login"; // 로그인 페이지로 이동
    }
    
    @RequestMapping("/join")
    public String join() {
    	return "join";
    }
}