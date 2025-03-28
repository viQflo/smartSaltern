package com.okjk.smartSaltern.service;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.repository.UserRepository;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    
    @Transactional
    public void register(User user) {
        try {
        	user.setUserRole("USER");
            user.setCreateDate(LocalDateTime.now());
            user.setUpdateDate(LocalDateTime.now());
            
         // 비밀번호 암호화
            String encodedPw = encoder.encode(user.getUserPw());
            user.setUserPw(encodedPw);
            
        	userRepository.saveAndFlush(user); // flush + commit
            System.out.println(">>> SAVE 성공");
            
            System.out.println("==== 등록 직후 전체 유저 조회 ====");
            userRepository.findAll().forEach(u -> System.out.println(u.getUserId()));
        } catch (Exception e) {
            System.out.println(">>> 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public User findByUserId(String userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    // 로그인 비밀번호 비교용 메소드
    public boolean matchPassword(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }
    
    // Delete
    public void deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else {
            throw new IllegalArgumentException("User not found: " + userId);
        }
    }
    
    
    // Update
    public User updateUser(String userId, User updatedUser) {
        return userRepository.findById(userId).map(user -> {
            user.setUserPw(updatedUser.getUserPw());
            user.setUserGender(updatedUser.getUserGender());
            user.setUserDepartment(updatedUser.getUserDepartment());
            user.setUpdateDate(LocalDateTime.now()); // 업데이트 시간 갱신
            return userRepository.save(user);
        }).orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
    }
    
    
}
