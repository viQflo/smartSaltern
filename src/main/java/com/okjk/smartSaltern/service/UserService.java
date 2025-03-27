package com.okjk.smartSaltern.service;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Transactional
    public void register(User user) {
        try {
        	user.setUserRole("USER");
            user.setCreateDate(LocalDateTime.now());
            user.setUpdateDate(LocalDateTime.now());
        	userRepository.saveAndFlush(user); // flush + commit
            System.out.println(">>> SAVE 성공");
            
            System.out.println("==== 등록 직후 전체 유저 조회 ====");
            userRepository.findAll().forEach(u -> System.out.println(u.getUserId()));
        } catch (Exception e) {
            System.out.println(">>> 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
