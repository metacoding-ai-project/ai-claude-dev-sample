package com.example.blog.global.config;

import com.example.blog.global.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/",
                        "/join-form",
                        "/join",
                        "/login-form",
                        "/login",
                        "/logout",
                        "/posts",
                        "/posts/search",
                        "/api/users/check",
                        "/h2-console/**",
                        "/css/**",
                        "/js/**"
                );
    }
}
