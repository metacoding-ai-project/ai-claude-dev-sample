package com.example.blog.domain.post.entity;

import com.example.blog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Post(String title, String content, String image, User user) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.user = user;
    }

    public void update(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
