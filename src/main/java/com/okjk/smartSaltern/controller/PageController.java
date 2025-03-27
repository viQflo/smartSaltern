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
    
    @RequestMapping("/login")
    public String login() {
    	return "login";
    }
    
    @RequestMapping("/join")
    public String join() {
    	return "join";
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
    
}