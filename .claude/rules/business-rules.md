# 비즈니스 규칙

> 전체 비즈니스 로직이 규칙이 여기에 다 담겨있다.

---

## 1. 역할

| 역할 | 설명 |
|------|------|
| 비회원 | 글 목록, 글 상세, 검색, 댓글 조회만 가능 |
| 회원 | 비회원 권한 + 글 작성, 댓글 작성 가능 |

- 관리자 역할 없음

---

## 2. 인증 규칙

- 회원가입: `username`, `password`, `email` 필수
- `username`은 가입 시 중복 체크 (AJAX `GET /api/users/check`)
- `username`은 전체에서 UNIQUE (DB 제약 + 서비스 레이어 검증)
- `email`은 UNIQUE (DB 제약)
- 소셜 로그인 없음 — 자체 가입만
- 로그인 성공 시 `userId`를 HttpSession에 저장
- 로그아웃: `session.invalidate()`

---

## 3. 접근 제어

로그인이 필요한 기능:
- 글 작성 (`/posts/new-form`, `POST /posts`)
- 글 수정 (`/posts/{id}/edit-form`, `POST /posts/{id}/edit`)
- 글 삭제 (`POST /posts/{id}/delete`)
- 댓글 작성 (`POST /posts/{postId}/comments`)
- 댓글 수정 (`GET/POST /posts/{postId}/comments/{id}/edit-form`)
- 댓글 삭제 (`POST /posts/{postId}/comments/{id}/delete`)

로그인 없이 접근 가능한 경로 (인터셉터 제외):
`/`, `/join-form`, `/join`, `/login-form`, `/login`, `/logout`, `/posts`, `/posts/search`, `/api/users/check`, `/h2-console/**`, `/css/**`, `/js/**`

- `/posts/{id}` (글 상세)는 excludePathPatterns에 포함하지 않고 `LoginInterceptor.preHandle()`에서 `GET + /posts/\d+` 패턴 매칭으로 허용
- `/posts/new-form`은 로그인 필요 — `/posts/*` 와일드카드로 일괄 제외하지 않는다

---

## 4. 권한 규칙 (소유권)

| 기능 | 비회원 | 회원(본인) | 회원(타인) |
|------|--------|-----------|-----------|
| 글 목록 / 상세 / 검색 | O | O | O |
| 댓글 조회 | O | O | O |
| 글 작성 / 댓글 작성 | X | O | O |
| 글 수정 / 삭제 | X | O | X |
| 댓글 수정 / 삭제 | X | O | X |

- 본인 글/댓글에만 수정·삭제 버튼 노출 (Mustache 조건부 렌더링)
- 권한 체크는 서비스 레이어에서 수행 (컨트롤러에서 하지 않음)
- 타인 글/댓글 수정·삭제 시도: 예외 발생

---

## 5. 게시글 규칙

- 제목(`title`), 본문(`content`) 필수
- 대표 이미지(`image`) 선택 (VARCHAR 500, 파일 경로)
- 페이징: 한 페이지 5개 고정

---

## 6. 댓글 규칙

- 1단계 댓글만 지원 — 대댓글 없음
- 내용(`content`) 필수, 최대 1000자

---

## 7. 검색 규칙

- 게시글 제목 기준 검색 (`title containing keyword`)
- 검색 결과도 5개/페이지 페이징 적용
- URL: `GET /posts/search?keyword=&page=0`

---

## 8. URL 설계 원칙

- 수정/삭제는 `POST` 사용 (HTML form은 GET/POST만 지원)
- 수정: `/posts/{id}/edit`, 삭제: `/posts/{id}/delete`
- API 엔드포인트: `/api/` 접두사

> 변경 이력: .claude/logs/business-rules-changelog.md
