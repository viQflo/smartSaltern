package com.okjk.smartSaltern.config;

import java.beans.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.okjk.smartSaltern.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
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
		authProvider.setPasswordEncoder(passwordEncoder()); // 비밀번호 인코딩
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
						.requestMatchers("/main", "/login", "/join", "/register", "/password_find", "/profile", "/css/**", "/js/**",
								"/images/**", "/assets/**")
						.permitAll().requestMatchers("/update", "/delete").authenticated() // 회원정보 수정 및 삭제는 인증된 사용자만 가능
						.anyRequest().authenticated())
				
				.formLogin(form -> form
						.loginPage("/login")
						.loginProcessingUrl("/login") // 로그인 처리 URL
						.usernameParameter("userId") // userId로 로그인
						.passwordParameter("userPw") // userPw
						.defaultSuccessUrl("/dashboard", true) // 로그인 성공 시 이동할 페이지
						.failureUrl("/login?error=true") // 로그인 실패 시 이동할 페이지
						.failureHandler((request, response, exception) -> {
							request.setAttribute("error", "아이디나 비밀번호가 잘못되었습니다.");
							response.sendRedirect("/login");
						}).permitAll())
				
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true) // 세션 무효화
						.clearAuthentication(true) // 인증 정보 삭제
						.permitAll())
				.csrf(csrf -> csrf.disable()); // CSRF 비활성화

		return http.build();
	}


	 
}
