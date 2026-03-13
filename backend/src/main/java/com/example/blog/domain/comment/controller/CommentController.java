package com.example.blog.domain.comment.controller;

import com.example.blog.domain.comment.dto.CommentResponse;
import com.example.blog.domain.comment.dto.CommentSaveRequest;
import com.example.blog.domain.comment.dto.CommentUpdateRequest;
import com.example.blog.domain.comment.service.CommentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public String save(@PathVariable Long postId, CommentSaveRequest dto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        commentService.save(postId, dto, userId);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{postId}/comments/{id}/edit-form")
    public String editForm(@PathVariable Long postId, @PathVariable Long id,
                           Model model, HttpSession session) {
        CommentResponse comment = commentService.findById(id);
        model.addAttribute("comment", comment);
        model.addAttribute("postId", postId);
        model.addAttribute("loginUserId", session.getAttribute("userId"));
        return "comment/edit";
    }

    @PostMapping("/posts/{postId}/comments/{id}/edit")
    public String edit(@PathVariable Long postId, @PathVariable Long id,
                       CommentUpdateRequest dto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        commentService.update(id, dto, userId);
        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/comments/{id}/delete")
    public String delete(@PathVariable Long postId, @PathVariable Long id,
                         HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        commentService.delete(id, userId);
        return "redirect:/posts/" + postId;
    }
}
