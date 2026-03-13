# 멀티유저 블로그 - Phase 3~6 통합 구현 계획

> **For agentic workers:** REQUIRED: Use superpowers:subagent-driven-development (if subagents available) or superpowers:executing-plans to implement this plan. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Phase 3(게시글) → Phase 4(댓글) → Phase 5(검색) → Phase 6(마무리)를 순서대로 완성한다. Phase 내 독립 태스크는 병렬로 실행한다.

**Architecture:** Spring Boot SSR 블로그. 각 도메인은 entity/repository/dto/service/controller 레이어로 분리. Mustache 템플릿으로 서버사이드 렌더링.

**Tech Stack:** Spring Boot 4.0.3, Spring Data JPA, Mustache, H2(dev), Lombok, Java 21

---

## 현재 상태

- Post entity (`domain/post/entity/Post.java`) — 완료
- PostRepository (`domain/post/repository/PostRepository.java`) — 완료 (findByTitleContaining 포함)
- Phase 2 인증 전체 — 완료

---

## 파일 맵

### 생성 파일

| 파일 | 역할 |
|------|------|
| `domain/post/dto/PostSaveRequest.java` | 글 작성 요청 DTO |
| `domain/post/dto/PostUpdateRequest.java` | 글 수정 요청 DTO |
| `domain/post/dto/PostResponse.java` | 글 응답 DTO |
| `domain/post/service/PostService.java` | 글 CRUD + 페이징 서비스 |
| `domain/post/controller/PostController.java` | 글 SSR 컨트롤러 |
| `domain/comment/entity/Comment.java` | 댓글 엔티티 |
| `domain/comment/repository/CommentRepository.java` | 댓글 리포지토리 |
| `domain/comment/dto/CommentSaveRequest.java` | 댓글 작성 요청 DTO |
| `domain/comment/dto/CommentUpdateRequest.java` | 댓글 수정 요청 DTO |
| `domain/comment/dto/CommentResponse.java` | 댓글 응답 DTO |
| `domain/comment/service/CommentService.java` | 댓글 CRUD 서비스 |
| `domain/comment/controller/CommentController.java` | 댓글 SSR 컨트롤러 |
| `templates/layout/header.mustache` | 공통 헤더 파셜 |
| `templates/layout/footer.mustache` | 공통 푸터 파셜 |
| `templates/post/list.mustache` | 글 목록 (5개/페이지, 페이징) |
| `templates/post/detail.mustache` | 글 상세 + 댓글 목록 + 본인 조건부 버튼 |
| `templates/post/new.mustache` | 글 작성 폼 |
| `templates/post/edit.mustache` | 글 수정 폼 |
| `templates/comment/edit.mustache` | 댓글 수정 폼 |

### 수정 파일

| 파일 | 변경 내용 |
|------|----------|
| `TODO.md` | 완료 항목 [x]로 업데이트 |
| `templates/user/join.mustache` | `{{> layout/header}}` / `{{> layout/footer}}` 추가 |
| `templates/user/login.mustache` | `{{> layout/header}}` / `{{> layout/footer}}` 추가 |

---

## Chunk 1: Phase 3 — 게시글 (병렬 가능 태스크 먼저)

### Task 1: Post DTOs 작성 [독립 — 병렬 실행 가능]

**Files:**
- Create: `backend/src/main/java/com/example/blog/domain/post/dto/PostSaveRequest.java`
- Create: `backend/src/main/java/com/example/blog/domain/post/dto/PostUpdateRequest.java`
- Create: `backend/src/main/java/com/example/blog/domain/post/dto/PostResponse.java`

- [ ] **Step 1: PostSaveRequest 작성**

```java
package com.example.blog.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostSaveRequest {
    private String title;
    private String content;
    private String image;
}
```

- [ ] **Step 2: PostUpdateRequest 작성**

```java
package com.example.blog.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostUpdateRequest {
    private String title;
    private String content;
    private String image;
}
```

- [ ] **Step 3: PostResponse 작성**

```java
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

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.image = post.getImage();
        this.username = post.getUser().getUsername();
    }
}
```

- [ ] **Step 4: 컴파일 확인**

```bash
cd backend && ./gradlew compileJava
```
Expected: BUILD SUCCESSFUL

---

### Task 2: Layout 템플릿 작성 [독립 — 병렬 실행 가능]

**Files:**
- Create: `backend/src/main/resources/templates/layout/header.mustache`
- Create: `backend/src/main/resources/templates/layout/footer.mustache`

- [ ] **Step 1: header.mustache 작성**

```html
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>블로그</title>
</head>
<body>
<nav>
    <a href="/posts">홈</a>
    {{#loginUserId}}
        <a href="/posts/new-form">글 작성</a>
        <a href="/logout">로그아웃</a>
    {{/loginUserId}}
    {{^loginUserId}}
        <a href="/login-form">로그인</a>
        <a href="/join-form">회원가입</a>
    {{/loginUserId}}
</nav>
<hr>
```

- [ ] **Step 2: footer.mustache 작성**

```html
<hr>
<footer>
    <p>© 2026 블로그</p>
</footer>
</body>
</html>
```

> 참고: 헤더에서 `loginUserId`를 사용하려면 컨트롤러에서 `model.addAttribute("loginUserId", session의 userId)`를 추가해야 한다. PostController에서 처리.

---

### Task 3: post/new.mustache + post/edit.mustache 작성 [독립 — 병렬 실행 가능]

**Files:**
- Create: `backend/src/main/resources/templates/post/new.mustache`
- Create: `backend/src/main/resources/templates/post/edit.mustache`

- [ ] **Step 1: post/new.mustache 작성**

```html
{{> layout/header}}
<h2>글 작성</h2>
<form action="/posts" method="post">
    <div>
        <label>제목</label>
        <input type="text" name="title" required>
    </div>
    <div>
        <label>내용</label>
        <textarea name="content" required></textarea>
    </div>
    <div>
        <label>이미지 경로 (선택)</label>
        <input type="text" name="image">
    </div>
    <button type="submit">작성</button>
    <a href="/posts">취소</a>
</form>
{{> layout/footer}}
```

- [ ] **Step 2: post/edit.mustache 작성**

```html
{{> layout/header}}
<h2>글 수정</h2>
<form action="/posts/{{id}}/edit" method="post">
    <div>
        <label>제목</label>
        <input type="text" name="title" value="{{title}}" required>
    </div>
    <div>
        <label>내용</label>
        <textarea name="content" required>{{content}}</textarea>
    </div>
    <div>
        <label>이미지 경로 (선택)</label>
        <input type="text" name="image" value="{{image}}">
    </div>
    <button type="submit">수정</button>
    <a href="/posts/{{id}}">취소</a>
</form>
{{> layout/footer}}
```

---

### Task 4: PostService 구현 [Task 1 완료 후]

**Files:**
- Create: `backend/src/main/java/com/example/blog/domain/post/service/PostService.java`

- [ ] **Step 1: PostService 작성**

```java
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
}
```

- [ ] **Step 2: 컴파일 확인**

```bash
cd backend && ./gradlew compileJava
```
Expected: BUILD SUCCESSFUL

---

### Task 5: PostController 구현 [Task 4 완료 후]

**Files:**
- Create: `backend/src/main/java/com/example/blog/domain/post/controller/PostController.java`

- [ ] **Step 1: PostController 작성**

```java
package com.example.blog.domain.post.controller;

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

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts")
    public String list(@RequestParam(defaultValue = "0") int page, Model model, HttpSession session) {
        Page<PostResponse> posts = postService.findAll(page);
        model.addAttribute("posts", posts.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("loginUserId", session.getAttribute("userId"));
        return "post/list";
    }

    @GetMapping("/posts/{id}")
    public String detail(@PathVariable Long id, Model model, HttpSession session) {
        PostResponse post = postService.findById(id);
        Long loginUserId = (Long) session.getAttribute("userId");
        model.addAttribute("post", post);
        model.addAttribute("isOwner", loginUserId != null && loginUserId.equals(
                postService.findById(id).getId()  // username 비교는 service에서 처리하지만 여기서는 userId로 직접 비교 필요
        ));
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
```

> **중요:** `detail()` 메서드의 `isOwner` 계산이 잘못되어 있다. `PostResponse`에 `userId`를 추가하거나, PostService에 `isOwner(Long postId, Long userId)` 메서드를 추가해서 올바르게 처리해야 한다. 아래 수정 단계 참고.

- [ ] **Step 2: PostResponse에 userId 필드 추가 (PostController가 isOwner 계산하도록)**

`PostResponse.java`에 `userId` 필드 추가:
```java
private final Long userId;

public PostResponse(Post post) {
    // ... 기존 필드들 ...
    this.userId = post.getUser().getId();
}
```

- [ ] **Step 3: PostController detail() 메서드 수정**

```java
@GetMapping("/posts/{id}")
public String detail(@PathVariable Long id, Model model, HttpSession session) {
    PostResponse post = postService.findById(id);
    Long loginUserId = (Long) session.getAttribute("userId");
    boolean isOwner = loginUserId != null && loginUserId.equals(post.getUserId());
    model.addAttribute("post", post);
    model.addAttribute("isOwner", isOwner);
    model.addAttribute("loginUserId", loginUserId);
    return "post/detail";
}
```

- [ ] **Step 4: 컴파일 확인**

```bash
cd backend && ./gradlew compileJava
```
Expected: BUILD SUCCESSFUL

---

### Task 6: post/list.mustache + post/detail.mustache [Task 5 완료 후 병렬 가능]

**Files:**
- Create: `backend/src/main/resources/templates/post/list.mustache`
- Create: `backend/src/main/resources/templates/post/detail.mustache`

- [ ] **Step 1: post/list.mustache 작성**

```html
{{> layout/header}}
<h2>게시글 목록</h2>
{{#posts}}
<div>
    <a href="/posts/{{id}}">{{title}}</a>
    <span>작성자: {{username}}</span>
</div>
{{/posts}}
{{^posts}}
<p>게시글이 없습니다.</p>
{{/posts}}

<div>
    {{#hasPrev}}
        <a href="/posts?page={{prevPage}}">이전</a>
    {{/hasPrev}}
    <span>{{pageNum}} / {{totalPages}}</span>
    {{#hasNext}}
        <a href="/posts?page={{nextPage}}">다음</a>
    {{/hasNext}}
</div>
{{> layout/footer}}
```

> **참고:** Mustache에서는 `currentPage - 1` 같은 계산을 직접 할 수 없으므로 컨트롤러에서 `hasPrev`, `prevPage`, `hasNext`, `nextPage`, `pageNum` 속성을 model에 추가해야 한다. PostController의 `list()` 메서드를 수정한다.

- [ ] **Step 2: PostController list() 페이징 model 보강**

```java
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
```

- [ ] **Step 3: post/detail.mustache 작성**

```html
{{> layout/header}}
<h2>{{post.title}}</h2>
<p>작성자: {{post.username}}</p>
<div>{{post.content}}</div>
{{#post.image}}
<img src="{{post.image}}" alt="대표 이미지">
{{/post.image}}

{{#isOwner}}
<a href="/posts/{{post.id}}/edit-form">수정</a>
<form action="/posts/{{post.id}}/delete" method="post" style="display:inline">
    <button type="submit" onclick="return confirm('삭제하시겠습니까?')">삭제</button>
</form>
{{/isOwner}}

<a href="/posts">목록으로</a>
{{> layout/footer}}
```

---

### Task 7: user 템플릿 레이아웃 파셜 적용 + TODO.md 업데이트

**Files:**
- Modify: `backend/src/main/resources/templates/user/join.mustache`
- Modify: `backend/src/main/resources/templates/user/login.mustache`
- Modify: `TODO.md`

- [ ] **Step 1: join.mustache에 레이아웃 파셜 적용**

`<!DOCTYPE html>` ~ `<body>` 부분을 `{{> layout/header}}`로, `</body></html>`을 `{{> layout/footer}}`로 교체.

- [ ] **Step 2: login.mustache에 레이아웃 파셜 적용**

동일하게 layout 파셜 적용.

- [ ] **Step 3: 애플리케이션 기동 확인**

```bash
cd backend && ./gradlew bootRun
```
- `http://localhost:8080/posts` — 글 목록 페이지
- `http://localhost:8080/join-form` — 회원가입 (헤더 표시 확인)
- 글 작성 → 목록 → 상세 → 수정 → 삭제 흐름 수동 확인

- [ ] **Step 4: TODO.md 항목 1~7 모두 [x]로 업데이트**

---

## Chunk 2: Phase 4 — 댓글

### Task 8: Comment 엔티티 + 리포지토리 + DTOs [독립]

**Files:**
- Create: `backend/src/main/java/com/example/blog/domain/comment/entity/Comment.java`
- Create: `backend/src/main/java/com/example/blog/domain/comment/repository/CommentRepository.java`
- Create: `backend/src/main/java/com/example/blog/domain/comment/dto/CommentSaveRequest.java`
- Create: `backend/src/main/java/com/example/blog/domain/comment/dto/CommentUpdateRequest.java`
- Create: `backend/src/main/java/com/example/blog/domain/comment/dto/CommentResponse.java`

- [ ] **Step 1: Comment 엔티티 작성**

```java
package com.example.blog.domain.comment.entity;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Comment(String content, Post post, User user) {
        this.content = content;
        this.post = post;
        this.user = user;
    }

    public void update(String content) {
        this.content = content;
    }
}
```

- [ ] **Step 2: CommentRepository 작성**

```java
package com.example.blog.domain.comment.repository;

import com.example.blog.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
```

- [ ] **Step 3: Comment DTOs 작성**

`CommentSaveRequest.java`:
```java
package com.example.blog.domain.comment.dto;
import lombok.Getter; import lombok.Setter;
@Getter @Setter
public class CommentSaveRequest {
    private String content;
}
```

`CommentUpdateRequest.java`:
```java
package com.example.blog.domain.comment.dto;
import lombok.Getter; import lombok.Setter;
@Getter @Setter
public class CommentUpdateRequest {
    private String content;
}
```

`CommentResponse.java`:
```java
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
```

- [ ] **Step 4: 컴파일 확인**

```bash
cd backend && ./gradlew compileJava
```

---

### Task 9: CommentService + CommentController [Task 8 완료 후]

**Files:**
- Create: `backend/src/main/java/com/example/blog/domain/comment/service/CommentService.java`
- Create: `backend/src/main/java/com/example/blog/domain/comment/controller/CommentController.java`

- [ ] **Step 1: CommentService 작성**

```java
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
```

- [ ] **Step 2: CommentController 작성**

```java
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
```

- [ ] **Step 3: 컴파일 확인**

```bash
cd backend && ./gradlew compileJava
```

---

### Task 10: 댓글 템플릿 + post/detail.mustache 댓글 섹션 추가 [Task 9 완료 후]

**Files:**
- Create: `backend/src/main/resources/templates/comment/edit.mustache`
- Modify: `backend/src/main/resources/templates/post/detail.mustache`
- Modify: `backend/src/main/java/com/example/blog/domain/post/controller/PostController.java` (detail 메서드 댓글 목록 추가)

- [ ] **Step 1: comment/edit.mustache 작성**

```html
{{> layout/header}}
<h2>댓글 수정</h2>
<form action="/posts/{{postId}}/comments/{{comment.id}}/edit" method="post">
    <div>
        <label>내용</label>
        <textarea name="content" required>{{comment.content}}</textarea>
    </div>
    <button type="submit">수정</button>
    <a href="/posts/{{postId}}">취소</a>
</form>
{{> layout/footer}}
```

- [ ] **Step 2: PostController에 CommentService 의존성 주입 및 detail() 수정**

`PostController`에 `CommentService` 추가:
```java
private final CommentService commentService;
```

`detail()` 메서드 수정:
```java
@GetMapping("/posts/{id}")
public String detail(@PathVariable Long id, Model model, HttpSession session) {
    PostResponse post = postService.findById(id);
    Long loginUserId = (Long) session.getAttribute("userId");
    boolean isOwner = loginUserId != null && loginUserId.equals(post.getUserId());
    List<CommentResponse> comments = commentService.findByPostId(id);
    // 각 댓글에 isOwner 플래그 계산 — Mustache에서 직접 처리 어려우므로 별도 Map 리스트 구성
    List<Map<String, Object>> commentList = comments.stream().map(c -> {
        Map<String, Object> map = new java.util.LinkedHashMap<>();
        map.put("id", c.getId());
        map.put("content", c.getContent());
        map.put("username", c.getUsername());
        map.put("isOwner", loginUserId != null && loginUserId.equals(c.getUserId()));
        return map;
    }).collect(java.util.stream.Collectors.toList());
    model.addAttribute("post", post);
    model.addAttribute("isOwner", isOwner);
    model.addAttribute("comments", commentList);
    model.addAttribute("loginUserId", loginUserId);
    return "post/detail";
}
```

- [ ] **Step 3: post/detail.mustache에 댓글 섹션 추가**

기존 `{{> layout/footer}}` 위에 추가:
```html
<hr>
<h3>댓글</h3>
{{#comments}}
<div>
    <span>{{username}}: {{content}}</span>
    {{#isOwner}}
    <a href="/posts/{{post.id}}/comments/{{id}}/edit-form">수정</a>
    <form action="/posts/{{post.id}}/comments/{{id}}/delete" method="post" style="display:inline">
        <button type="submit">삭제</button>
    </form>
    {{/isOwner}}
</div>
{{/comments}}
{{^comments}}
<p>댓글이 없습니다.</p>
{{/comments}}

{{#loginUserId}}
<form action="/posts/{{post.id}}/comments" method="post">
    <textarea name="content" placeholder="댓글을 입력하세요" required></textarea>
    <button type="submit">댓글 작성</button>
</form>
{{/loginUserId}}
```

> **주의:** Mustache에서 `{{post.id}}`는 현재 컨텍스트가 `comments` 루프 내부이므로 상위 컨텍스트 접근을 위해 `{{../post.id}}` 또는 별도로 `postId`를 model에 추가해야 한다. `PostController.detail()`에서 `model.addAttribute("postId", id)` 추가 후 `{{postId}}`로 사용.

- [ ] **Step 4: WebConfig 인터셉터 제외 경로에 댓글 편집 폼 경로 확인**

`WebConfig`의 제외 경로(`/posts/*`)가 `/posts/{id}`를 커버하는지 확인. 댓글 수정/삭제는 로그인 필요이므로 제외 경로에 추가하지 않음.

- [ ] **Step 5: 애플리케이션 기동 및 댓글 기능 확인**

```bash
cd backend && ./gradlew bootRun
```
- 글 상세 페이지에서 댓글 작성 → 목록 표시 확인
- 본인 댓글에만 수정/삭제 버튼 표시 확인

---

## Chunk 3: Phase 5 — 검색

### Task 11: 검색 기능 구현 [Phase 3 완료 후]

**Files:**
- Modify: `backend/src/main/java/com/example/blog/domain/post/service/PostService.java`
- Modify: `backend/src/main/java/com/example/blog/domain/post/controller/PostController.java`
- Create: `backend/src/main/resources/templates/post/search.mustache`

- [ ] **Step 1: PostService에 search 메서드 추가**

```java
public Page<PostResponse> search(String keyword, int page) {
    PageRequest pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
    return postRepository.findByTitleContaining(keyword, pageable).map(PostResponse::new);
}
```

- [ ] **Step 2: PostController에 검색 엔드포인트 추가**

```java
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
```

- [ ] **Step 3: post/search.mustache 작성**

```html
{{> layout/header}}
<h2>"{{keyword}}" 검색 결과</h2>
<form action="/posts/search" method="get">
    <input type="text" name="keyword" value="{{keyword}}">
    <button type="submit">검색</button>
</form>

{{#posts}}
<div>
    <a href="/posts/{{id}}">{{title}}</a>
    <span>작성자: {{username}}</span>
</div>
{{/posts}}
{{^posts}}
<p>검색 결과가 없습니다.</p>
{{/posts}}

<div>
    {{#hasPrev}}
        <a href="/posts/search?keyword={{keyword}}&page={{prevPage}}">이전</a>
    {{/hasPrev}}
    <span>{{pageNum}} / {{totalPages}}</span>
    {{#hasNext}}
        <a href="/posts/search?keyword={{keyword}}&page={{nextPage}}">다음</a>
    {{/hasNext}}
</div>
<a href="/posts">목록으로</a>
{{> layout/footer}}
```

- [ ] **Step 4: post/list.mustache에 검색 폼 추가**

목록 상단에 검색 폼 추가:
```html
<form action="/posts/search" method="get">
    <input type="text" name="keyword" placeholder="제목 검색">
    <button type="submit">검색</button>
</form>
```

- [ ] **Step 5: WebConfig 인터셉터 제외 경로에 /posts/search 확인**

`/posts/*` 패턴이 `/posts/search`를 포함하는지 확인. Spring의 `/**` 패턴이므로 보통 커버됨. 확인 후 필요시 명시적으로 추가.

---

## Chunk 4: Phase 6 — 마무리

### Task 12: 전체 기능 검증 + 인터셉터 경로 확인

- [ ] **Step 1: WebConfig 제외 경로 최종 확인**

현재 제외 경로: `/`, `/join-form`, `/join`, `/login-form`, `/login`, `/posts`, `/posts/*`, `/h2-console/**`, `/css/**`, `/js/**`

확인 사항:
- `/posts/search` — `/posts/*`에 포함되므로 OK (비회원도 검색 가능)
- `/posts/{id}` — `/posts/*`에 포함 OK
- `/posts/{id}/edit-form` — `/posts/*`에 **포함 안 됨** (2단계 이상). 로그인 필요하므로 제외하지 않아도 됨
- `/posts/new-form` — `/posts/*`에 포함됨. **주의:** 비회원도 new-form 접근 가능해지므로 제외 경로에서 제거해야 한다.

제외 경로에서 `/posts/new-form` 제거 또는 `/posts/*`를 `/posts` 로만 두고 `/posts/{id}` 를 별도로 명시한다.

수정 방향: `/posts/*` 대신 필요한 경로만 명시적으로 추가:
```java
.excludePathPatterns(
    "/", "/join-form", "/join", "/login-form", "/login",
    "/posts", "/posts/search",
    // /posts/{id} (숫자만) — 정규식 불가, /posts/* 유지하되 new-form은 제거
    // 대안: /posts/*는 유지하고 new-form은 컨트롤러에서 세션 체크
    "/h2-console/**", "/css/**", "/js/**"
)
```

> 가장 단순한 해결: `/posts/*` 제외 경로 유지하되 `new-form`은 로그인 체크를 컨트롤러에서 직접 처리하거나, 인터셉터에서 `/posts/new-form`을 다시 포함시킨다.

권장 접근: `WebConfig`에서 `/posts/*`를 제외 경로에 두고, `/posts/new-form`을 **제외 경로에서 제거** (즉 인터셉터가 체크하도록) 방식으로 수정:

```java
.excludePathPatterns(
    "/", "/join-form", "/join", "/login-form", "/login",
    "/posts", "/posts/search",
    "/h2-console/**", "/css/**", "/js/**"
)
```

그리고 `/posts/{id}` (상세)는 인터셉터를 통과하므로 `LoginInterceptor`에서 GET 요청은 허용하도록 수정하거나, `excludePathPatterns`에 `/posts/[0-9]+` 패턴을 추가 (Spring은 정규식 지원 안 함, AntMatcher 사용).

최종 결정: 제외 경로를 아래와 같이 명확하게 정의:
```java
.excludePathPatterns(
    "/", "/join-form", "/join", "/login-form", "/login", "/logout",
    "/posts", "/posts/search",
    "/h2-console/**", "/css/**", "/js/**"
)
```

그리고 `LoginInterceptor`에서 `GET /posts/{id}` (숫자) 요청은 허용하도록 preHandle에 조건 추가:
```java
// GET 요청이고 /posts/{숫자} 패턴이면 통과
if (request.getMethod().equals("GET") &&
    request.getRequestURI().matches("/posts/\\d+")) {
    return true;
}
```

- [ ] **Step 2: 애플리케이션 전체 기동 테스트**

```bash
cd backend && ./gradlew bootRun
```

수동 검증 체크리스트:
- [ ] 비회원: 목록, 상세, 검색 접근 가능
- [ ] 비회원: 글 작성 폼 접근 시 로그인 페이지로 리다이렉트
- [ ] 회원가입 → 로그인 → 글 작성 → 목록에서 확인
- [ ] 글 상세에서 본인 글에만 수정/삭제 버튼 표시
- [ ] 글 수정 → 변경 내용 확인
- [ ] 글 삭제 → 목록으로 이동
- [ ] 댓글 작성 → 표시 확인
- [ ] 본인 댓글에만 수정/삭제 버튼 표시
- [ ] 제목 검색 → 결과 페이징 확인
- [ ] 페이징: 5개 초과 글 작성 후 페이지 이동 확인

---

## 병렬 실행 가이드

```
[즉시 병렬 실행 가능]
  Task 1: Post DTOs
  Task 2: Layout 템플릿
  Task 3: new.mustache + edit.mustache

[Task 1 완료 후]
  Task 4: PostService

[Task 4 완료 후]
  Task 5: PostController

[Task 5 완료 후 병렬]
  Task 6: list.mustache + detail.mustache
  Task 7: user 템플릿 레이아웃 + TODO.md 업데이트

[Phase 3 완료 후 병렬]
  Task 8: Comment 엔티티 + 리포지토리 + DTOs

[Task 8 완료 후]
  Task 9: CommentService + CommentController
  Task 11: 검색 기능 (PostService/Controller 수정 + search.mustache)

[Task 9 완료 후]
  Task 10: 댓글 템플릿 + detail.mustache 댓글 섹션

[전체 완료 후]
  Task 12: WebConfig 인터셉터 검증 + 전체 기능 테스트
```
