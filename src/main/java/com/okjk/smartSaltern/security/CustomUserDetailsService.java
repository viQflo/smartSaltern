package com.okjk.smartSaltern.security;

import com.okjk.smartSaltern.entity.User;
import com.okjk.smartSaltern.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        System.out.println("로그인 시도: " + userId);  // 로그인 시도 로그 추가
       // findById()가 Optional을 반환해야 합니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + userId));

        System.out.println("사용자 정보: " + user.getUserId());  // 사용자 정보가 정상적으로 로드되는지 확인

        return new CustomUserDetails(user);
    }
}
