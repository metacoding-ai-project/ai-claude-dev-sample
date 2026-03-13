package com.example.blog.domain.post.dto;

import com.example.blog.domain.post.entity.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String image;
    private final String username;
    private final Long userId;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.username = post.getUser().getUsername();
        this.userId = post.getUser().getId();
    }
}
