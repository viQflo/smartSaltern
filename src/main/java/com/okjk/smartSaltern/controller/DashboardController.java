package com.okjk.smartSaltern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.okjk.smartSaltern.entity.User;

import jakarta.servlet.http.HttpSession;

@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String dashboard(HttpSession session, Model model) {
	    User loginUser = (User) session.getAttribute("loginUser");
	    
	    if (loginUser == null) {
	        return "redirect:/login";
	    }

	    model.addAttribute("loginUser", loginUser);
	    return "dashboard";
	}
}
