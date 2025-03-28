package com.okjk.smartSaltern.config;

import com.okjk.smartSaltern.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(@Lazy CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());// 비밀번호 인코딩
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	
            .authenticationProvider(authenticationProvider()) // 인증 제공자 설정
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/", "/main", "/login", "/join", "/register", "/password_find", // 비밀번호 찾기 페이지 추가
                    "/css/**", "/js/**", "/images/**", "/assets/**"
                ).permitAll() // 비밀번호 찾기 페이지와 기타 리소스들을 인증 없이 접근 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
		        )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .loginProcessingUrl("/login") // 로그인 처리 URL
	            .usernameParameter("userId")  // userId로 로그인
	            .passwordParameter("userPw")  // userPw
	            .defaultSuccessUrl("/dashboard", true) // 로그인 성공 시 이동할 페이지
	            .failureUrl("/login?error=true") // 로그인 실패 시 이동할 페이지
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutUrl("/logout")
	            .logoutSuccessUrl("/login?logout")
	            .permitAll()
	        )
	        .csrf(csrf -> csrf.disable()); //활성화
        		
	        	

        return http.build();
    }
}
