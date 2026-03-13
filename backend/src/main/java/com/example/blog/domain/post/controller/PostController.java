package com.example.blog.domain.post.controller;

import com.example.blog.domain.comment.dto.CommentResponse;
import com.example.blog.domain.comment.service.CommentService;
import com.example.blog.domain.post.dto.PostResponse;
import com.example.blog.domain.post.dto.PostSaveRequest;
import com.example.blog.domain.post.dto.PostUpdateRequest;
import com.example.blog.domain.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    @GetMapping("/posts")
    public String list(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        Page<PostResponse> posts = postService.findAll(page);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("pageNum", page + 1);
        model.addAttribute("hasPrev", page > 0);
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("hasNext", page < posts.getTotalPages() - 1);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("loginUserId", session.getAttribute("userId"));
        return "post/list";
    }

    @GetMapping("/posts/search")
    public String search(@RequestParam String keyword,
                         @RequestParam(defaultValue = "0") int page,
                         Model model, HttpSession session) {
        Page<PostResponse> posts = postService.search(keyword, page);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("pageNum", page + 1);
        model.addAttribute("hasPrev", page > 0);
        model.addAttribute("prevPage", page - 1);
        model.addAttribute("hasNext", page < posts.getTotalPages() - 1);
        model.addAttribute("nextPage", page + 1);
        model.addAttribute("loginUserId", session.getAttribute("userId"));
        return "post/search";
    }

    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        PostResponse post = postService.findById(id);
        Long loginUserId = (Long) session.getAttribute("userId");
        boolean isOwner = loginUserId != null && loginUserId.equals(post.getUserId());
        List<CommentResponse> comments = commentService.findByPostId(id);
        List<Map<String, Object>> commentList = comments.stream().map(c -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("id", c.getId());
            map.put("content", c.getContent());
            map.put("username", c.getUsername());
            map.put("isOwner", loginUserId != null && loginUserId.equals(c.getUserId()));
            map.put("postId", id);
            return map;
        }).collect(Collectors.toList());
        model.addAttribute("post", post);
        model.addAttribute("isOwner", isOwner);
        model.addAttribute("comments", commentList);
        model.addAttribute("postId", id);
        model.addAttribute("loginUserId", loginUserId);
        return "post/detail";
    }

    @GetMapping("/posts/new-form")
    public String newForm(Model model, HttpSession session) {
        model.addAttribute("loginUserId", session.getAttribute("userId"));
        return "post/new";
    }

    @PostMapping("/posts")
    public String save(PostSaveRequest dto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        Long postId = postService.save(dto, userId);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{id}/edit-form")
    public String editForm(@PathVariable Long id, Model model, HttpSession session) {
        PostResponse post = postService.findById(id);
        model.addAttribute("post", post);
        model.addAttribute("loginUserId", session.getAttribute("userId"));
        return "post/edit";
    }

    @PostMapping("/posts/{id}/edit")
    public String edit(@PathVariable Long id, PostUpdateRequest dto, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        postService.update(id, dto, userId);
        return "redirect:/posts/" + id;
    }

    @PostMapping("/posts/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        postService.delete(id, userId);
        return "redirect:/posts";
    }
}
