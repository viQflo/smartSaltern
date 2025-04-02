package com.okjk.smartSaltern.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import  org.springframework.ui.Model;

@ControllerAdvice
public class GlobalExceptionHandler {


	// ✅ 1. 인증 관련 예외 처리 (로그인 실패, 접근 권한 문제)
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public String handleAuthException(Exception ex, Model model) {
        model.addAttribute("error", "로그인 오류: " + ex.getMessage());
        return "login";  // 로그인 페이지로 이동
    }


	
}
	

