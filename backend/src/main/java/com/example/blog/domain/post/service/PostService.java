package com.example.blog.domain.post.service;

import com.example.blog.domain.post.dto.PostResponse;
import com.example.blog.domain.post.dto.PostSaveRequest;
import com.example.blog.domain.post.dto.PostUpdateRequest;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Page<PostResponse> findAll(int page) {
        PageRequest pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.findAll(pageable).map(PostResponse::new);
    }

    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return new PostResponse(post);
    }

    @Transactional
    public Long save(PostSaveRequest dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        Post post = new Post(dto.getTitle(), dto.getContent(), dto.getImage(), user);
        return postRepository.save(post).getId();
    }

    @Transactional
    public void update(Long id, PostUpdateRequest dto, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        post.update(dto.getTitle(), dto.getContent(), dto.getImage());
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        postRepository.delete(post);
    }

    public Page<PostResponse> search(String keyword, int page) {
        PageRequest pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.findByTitleContaining(keyword, pageable).map(PostResponse::new);
    }
}
