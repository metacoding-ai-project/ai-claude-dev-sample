# 구현 체크리스트 (Tasks): 멀티유저 블로그 플랫폼

> phases.md(마일스톤)와 todo.md(오늘 할 일) 사이의 중간 계층
> Phase별 세부 구현 항목을 영구 기록 — 완료 시 `- [x]`로 업데이트

---

## Phase 1 — 프로젝트 셋업

- [x] Spring Boot 프로젝트 생성 (Gradle)
- [x] 의존성 추가 (spring-boot-starter-web, spring-boot-starter-data-jpa, spring-boot-starter-mustache, h2, mysql-connector-j)
- [x] 패키지 구조 생성
  - [x] `domain/user`
  - [x] `domain/post`
  - [x] `domain/comment`
  - [x] `global`
- [x] `application.properties` 기본 설정 (공통 설정, spring.profiles.active=dev)
- [x] `application-dev.properties` (H2 인메모리, ddl-auto=create-drop, h2-console 활성화)
- [x] `application-prod.properties` (MySQL, ddl-auto=validate)

---

## Phase 2 — 인증

**엔티티 / 리포지토리**
- [x] `User` 엔티티 (id, username, password, email)
- [x] `UserRepository` (JpaRepository)

**서비스**
- [x] `UserService.join()` — 회원가입 (username 중복 체크 포함)
- [x] `UserService.login()` — 로그인 (비밀번호 검증 → 세션 저장)
- [x] `UserService.isUsernameTaken()` — username 중복 여부 반환

**컨트롤러**
- [x] `UserController`
  - [x] `GET /join-form` — 회원가입 폼 렌더링
  - [x] `POST /join` — 회원가입 처리 → `/login-form` 리다이렉트
  - [x] `GET /login-form` — 로그인 폼 렌더링
  - [x] `POST /login` — 로그인 처리 → `/posts` 리다이렉트
  - [x] `GET /logout` — 세션 무효화 → `/posts` 리다이렉트
- [x] `UserApiController` (@RestController)
  - [x] `GET /api/users/check` — username 중복 체크 → `ApiResponse<Boolean>` 반환

**공통**
- [x] `ApiResponse<T>` DTO (status, msg, data)
- [x] `LoginInterceptor` (HandlerInterceptor 구현 — 미로그인 시 `/login-form` 리다이렉트)
- [x] `WebMvcConfigurer`에 `LoginInterceptor` 등록 (로그인 필요 URL 패턴 지정)

**템플릿**
- [x] `user/join.mustache` — 회원가입 폼
- [x] `user/login.mustache` — 로그인 폼

---

## Phase 3 — 게시글

**엔티티 / 리포지토리**
- [x] `Post` 엔티티 (id, title, content, image, user_id FK)
- [x] `PostRepository` (JpaRepository)

**서비스**
- [x] `PostService.findAll(pageable)` — 목록 조회 (5개/페이지 페이징)
- [x] `PostService.findById(id)` — 상세 조회
- [x] `PostService.save(dto, userId)` — 작성
- [x] `PostService.update(id, dto, userId)` — 수정 (본인 검증)
- [x] `PostService.delete(id, userId)` — 삭제 (본인 검증)

**컨트롤러**
- [x] `PostController`
  - [x] `GET /posts` — 글 목록 (page 파라미터, 기본값 0)
  - [x] `GET /posts/{id}` — 글 상세
  - [x] `GET /posts/new-form` — 글 작성 폼 (로그인 필요)
  - [x] `POST /posts` — 글 작성 처리 → `/posts/{id}` 리다이렉트
  - [x] `GET /posts/{id}/edit-form` — 글 수정 폼 (본인만)
  - [x] `POST /posts/{id}/edit` — 글 수정 처리 → `/posts/{id}` 리다이렉트
  - [x] `POST /posts/{id}/delete` — 글 삭제 → `/posts` 리다이렉트 (본인만)

**템플릿**
- [x] `post/list.mustache` — 글 목록 (5개/페이지, 페이징 네비게이션)
- [x] `post/detail.mustache` — 글 상세 (본인 글에만 수정·삭제 버튼 조건부 렌더링)
- [x] `post/new.mustache` — 글 작성 폼
- [x] `post/edit.mustache` — 글 수정 폼

---

## Phase 4 — 댓글

**엔티티 / 리포지토리**
- [x] `Comment` 엔티티 (id, content, post_id FK, user_id FK)
- [x] `CommentRepository` (JpaRepository)

**서비스**
- [x] `CommentService.save(postId, dto, userId)` — 댓글 작성
- [x] `CommentService.update(id, dto, userId)` — 댓글 수정 (본인 검증)
- [x] `CommentService.delete(id, userId)` — 댓글 삭제 (본인 검증)

**컨트롤러**
- [x] `CommentController`
  - [x] `POST /posts/{postId}/comments` — 댓글 작성 → `/posts/{postId}` 리다이렉트 (로그인 필요)
  - [x] `GET /posts/{postId}/comments/{id}/edit-form` — 댓글 수정 폼 (본인만)
  - [x] `POST /posts/{postId}/comments/{id}/edit` — 댓글 수정 처리 → `/posts/{postId}` 리다이렉트
  - [x] `POST /posts/{postId}/comments/{id}/delete` — 댓글 삭제 → `/posts/{postId}` 리다이렉트 (본인만)

**템플릿**
- [x] `post/detail.mustache`에 댓글 목록 + 작성 폼 섹션 추가
- [x] 본인 댓글에만 수정·삭제 버튼 조건부 렌더링

---

## Phase 5 — 검색

- [x] `PostRepository.findByTitleContaining(keyword, pageable)` 메서드 추가
- [x] `PostService.search(keyword, pageable)` — 검색 결과 반환 (5개/페이지)
- [x] `PostController`
  - [x] `GET /posts/search` — 검색 처리 (keyword, page 파라미터, page 기본값 0)
- [x] `post/search.mustache` — 검색 결과 페이지 (키워드 표시, 5개/페이지, 페이징 네비게이션)

---

## Phase 6 — 마무리

**공통 레이아웃**
- [x] `layout/header.mustache` 파셜 정리 (공통 헤더 분리)
- [x] `layout/footer.mustache` 파셜 정리 (공통 푸터 분리)
- [x] 전체 mustache 템플릿에 공통 레이아웃 파셜 적용

**인터셉터 / 권한**
- [x] 인터셉터 적용 URL 패턴 최종 정리
  - 로그인 필요: `/posts/new-form`, `POST /posts`, `/posts/*/edit-form`, `POST /posts/*/edit`, `POST /posts/*/delete`, `POST /posts/*/comments`, `/posts/*/comments/*/edit-form`, `POST /posts/*/comments/*/edit`, `POST /posts/*/comments/*/delete`
- [x] 비로그인 접근 시 `/login-form` 리다이렉트 동작 검증

**예외 처리**
- [ ] 403 Forbidden — 타인의 글/댓글 수정·삭제 시도
- [ ] 404 Not Found — 존재하지 않는 글·댓글 조회

**검증 / 배포 준비**
- [ ] prd.md 수용 기준 전체 항목 체크
- [ ] MySQL 연결 및 `application-prod.properties` 검증
- [ ] `ddl-auto=validate` 환경에서 스키마 일치 확인
