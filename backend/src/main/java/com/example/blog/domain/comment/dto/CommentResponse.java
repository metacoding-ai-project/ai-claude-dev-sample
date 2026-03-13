package com.example.blog.domain.comment.dto;

import com.example.blog.domain.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponse {
    private final Long id;
    private final String content;
    private final String username;
    private final Long userId;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.userId = comment.getUser().getId();
    }
}
