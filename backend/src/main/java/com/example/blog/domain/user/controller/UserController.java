package com.example.blog.domain.user.controller;

import com.example.blog.domain.user.dto.JoinRequest;
import com.example.blog.domain.user.dto.LoginRequest;
import com.example.blog.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/join-form")
    public String joinForm() {
        return "user/join";
    }

    @PostMapping("/join")
    public String join(JoinRequest dto) {
        userService.join(dto);
        return "redirect:/login-form";
    }

    @GetMapping("/login-form")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(LoginRequest dto, HttpSession session) {
        userService.login(dto, session);
        return "redirect:/posts";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/posts";
    }
}
