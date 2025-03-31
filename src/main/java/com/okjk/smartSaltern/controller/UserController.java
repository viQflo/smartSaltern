package com.okjk.smartSaltern.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.service.UserService;

import org.springframework.ui.Model;

@Controller
public class UserController {

	 @ExceptionHandler(Exception.class)
	    public String handleException(Exception e, Model model) {
	        // 예외 메시지 로그에 기록
	        e.printStackTrace();

	        // 사용자에게 적절한 메시지를 표시
	        model.addAttribute("error", e.getMessage());
	        return "errorPage";  // errorPage는 에러 화면으로 이동하는 템플릿
	    }
	
	
    @Autowired
    private UserService userService;
    
    // 회원가입
    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/login";
    }
    
    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", "로그인 실패! 아이디와 비밀번호를 다시 확인해주세요.");
        }
        return "login"; // login.html 또는 login.jsp로 연결될 페이지 이름
    }
    
    
    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        String userId = authentication.name(); // 현재 로그인한 사용자 ID 가져오기
        boolean isDeleted = userService.deleteUser(userId);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully: " + userId);
        } else {
            return ResponseEntity.status(400).body("User deletion failed: " + userId);
        }
    }
    
    // Update
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(Authentication authentication, @RequestBody User updatedUser) {
        String userId = authentication.name(); // 로그인된 사용자의 ID
        User user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(400).body(null); // 실패 시
        }
    }
    
 
    
    
    
}
