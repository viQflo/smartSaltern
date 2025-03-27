package com.okjk.smartSaltern.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") // 전체 경로에 적용
                .excludePathPatterns(
                        "/", "/login", "/logout", "/join",
                        "/css/**", "/js/**", "/images/**", "/assets/**"
                ); // 로그인 없이 접근 허용할 경로들
    }
}
