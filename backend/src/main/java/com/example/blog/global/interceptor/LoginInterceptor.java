package com.example.blog.global.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Allow GET /posts/{id} without login (public post detail)
        if ("GET".equals(request.getMethod()) &&
            request.getRequestURI().matches("/posts/\\d+")) {
            return true;
        }
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect("/login-form");
            return false;
        }
        return true;
    }
}
