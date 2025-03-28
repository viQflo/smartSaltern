package com.okjk.smartSaltern.controller;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.security.CustomUserDetails;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/dashboard")
	public String dashboard(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
	    User loginUser = customUserDetails.getUser();
	    model.addAttribute("loginUser", loginUser);
	    return "dashboard";
	}
}
