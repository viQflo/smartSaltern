package com.okjk.smartSaltern.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import  org.springframework.ui.Model;

@ControllerAdvice
public class GlobalExceptionHandler {


	    // 예외 처리 메서드
	@ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("error", "오류가 발생했습니다: " + ex.getMessage());
        return "error";  // error.html로 이동
    }
	
}
	

