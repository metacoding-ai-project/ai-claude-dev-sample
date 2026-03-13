package com.example.blog.domain.user.service;

import com.example.blog.domain.user.dto.JoinRequest;
import com.example.blog.domain.user.dto.LoginRequest;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void join(JoinRequest dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        userRepository.save(new User(dto.getUsername(), dto.getPassword(), dto.getEmail()));
    }

    public void login(LoginRequest dto, HttpSession session) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));
        if (!user.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        session.setAttribute("userId", user.getId());
    }

    public boolean isUsernameTaken(String username) {
        return userRepository.existsByUsername(username);
    }
}
