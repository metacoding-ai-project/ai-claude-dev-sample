package com.example.blog.domain.comment.service;

import com.example.blog.domain.comment.dto.CommentResponse;
import com.example.blog.domain.comment.dto.CommentSaveRequest;
import com.example.blog.domain.comment.dto.CommentUpdateRequest;
import com.example.blog.domain.comment.entity.Comment;
import com.example.blog.domain.comment.repository.CommentRepository;
import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import com.example.blog.domain.user.entity.User;
import com.example.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<CommentResponse> findByPostId(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void save(Long postId, CommentSaveRequest dto, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        commentRepository.save(new Comment(dto.getContent(), post, user));
    }

    public CommentResponse findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        return new CommentResponse(comment);
    }

    @Transactional
    public void update(Long id, CommentUpdateRequest dto, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        comment.update(dto.getContent());
    }

    @Transactional
    public void delete(Long id, Long userId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if (!comment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
}
