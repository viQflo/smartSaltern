package com.okjk.smartSaltern.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@ModelAttribute User user) {
        userService.register(user);
        return "redirect:/login";
    }
    
    
    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(Authentication authentication) {
        String userId = authentication.name(); // 현재 로그인한 사용자 ID 가져오기
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully: " + userId);
    }
    
    
    // Update
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(Authentication authentication, @RequestBody User updatedUser) {
        String userId = authentication.name();
        User user = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(user);
    }
    
    
}
