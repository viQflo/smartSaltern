package com.okjk.smartSaltern.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/")
    public String home() {
        return "index"; // templates/index.html 파일을 반환
    }
    
    @RequestMapping("/main")
    public String main() {
        return "main"; // templates/index.html 파일을 반환
    }
    
    @RequestMapping("/header")
    public String header() {
        return "header"; // templates/index.html 파일을 반환
    }
    
    @RequestMapping("/login")
    public String login() {
    	return "login";
    }
    
    @RequestMapping("/join")
    public String join() {
    	return "join";
    }
}