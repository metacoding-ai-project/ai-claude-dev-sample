# 기능 명세서: 멀티유저 블로그 플랫폼

---

## 1. 회원 (User)

| 기능 | Method | URL | 요청 파라미터 | 응답 |
|------|--------|-----|--------------|------|
| 회원가입 폼 | GET | /join-form | - | 회원가입 페이지 |
| 회원가입 처리 | POST | /join | username, password, email | 로그인 페이지로 리다이렉트 |
| username 중복 체크 | GET | /api/users/check | username | `ApiResponse<Boolean>` (AJAX) |
| 로그인 폼 | GET | /login-form | - | 로그인 페이지 |
| 로그인 처리 | POST | /login | username, password | 글 목록으로 리다이렉트 |
| 로그아웃 | GET | /logout | - | 글 목록으로 리다이렉트 |

---

## 2. 게시글 (Post)

| 기능 | Method | URL | 요청 파라미터 | 응답 |
|------|--------|-----|--------------|------|
| 글 목록 | GET | /posts | page (기본값: 0) | 글 목록 페이지 (5개/페이지) |
| 글 상세 | GET | /posts/{id} | - | 글 상세 페이지 |
| 글 작성 폼 | GET | /posts/new-form | - | 글 작성 페이지 (로그인 필요) |
| 글 작성 처리 | POST | /posts | title, content, image | 글 상세 페이지로 리다이렉트 |
| 글 수정 폼 | GET | /posts/{id}/edit-form | - | 글 수정 페이지 (본인만) |
| 글 수정 처리 | POST | /posts/{id}/edit | title, content, image | 글 상세 페이지로 리다이렉트 |
| 글 삭제 | POST | /posts/{id}/delete | - | 글 목록으로 리다이렉트 (본인만) |
| 글 검색 | GET | /posts/search | keyword, page (기본값: 0) | 검색 결과 페이지 (5개/페이지) |

---

## 3. 댓글 (Comment)

| 기능 | Method | URL | 요청 파라미터 | 응답 |
|------|--------|-----|--------------|------|
| 댓글 작성 | POST | /posts/{postId}/comments | content | 글 상세 페이지로 리다이렉트 (로그인 필요) |
| 댓글 수정 폼 | GET | /posts/{postId}/comments/{id}/edit-form | - | 댓글 수정 페이지 (본인만) |
| 댓글 수정 처리 | POST | /posts/{postId}/comments/{id}/edit | content | 글 상세 페이지로 리다이렉트 |
| 댓글 삭제 | POST | /posts/{postId}/comments/{id}/delete | - | 글 상세 페이지로 리다이렉트 (본인만) |

---

## 4. 권한 규칙 요약

| 기능 | 비회원 | 회원 (본인) | 회원 (타인) |
|------|--------|------------|------------|
| 글 목록 / 상세 / 검색 | O | O | O |
| 댓글 조회 | O | O | O |
| 글 작성 | X | O | O |
| 댓글 작성 | X | O | O |
| 글 수정 / 삭제 | X | O | X |
| 댓글 수정 / 삭제 | X | O | X |
