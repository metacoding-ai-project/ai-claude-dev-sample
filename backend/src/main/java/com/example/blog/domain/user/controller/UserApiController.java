package com.example.blog.domain.user.controller;

import com.example.blog.domain.user.service.UserService;
import com.example.blog.global.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/check")
    public ApiResponse<Boolean> checkUsername(@RequestParam String username) {
        return ApiResponse.ok(userService.isUsernameTaken(username));
    }
}
