package com.okjk.smartSaltern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session) {
	    if (session.getAttribute("loginUser") == null) {
	        return "redirect:/login"; // 로그인 안 했으면 로그인 페이지로
	    }
	    return "dashboard"; // 로그인 되어 있으면 대시보드 보여주기
	}
}
