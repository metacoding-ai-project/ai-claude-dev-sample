# TODO - 2026-03-13

- [x] 1. User 엔티티·리포지토리 생성
  `User` 엔티티(id, username, password, email) + `UserRepository` 작성

- [x] 2. UserService 구현
  `join()`, `login()`, `isUsernameTaken()` 메서드 구현

- [x] 3. UserController 구현
  회원가입/로그인/로그아웃 GET·POST 엔드포인트 5개 작성

- [x] 4. UserApiController 구현
  `GET /api/users/check` — username 중복 체크 REST API

- [x] 5. ApiResponse DTO + LoginInterceptor 공통 구현
  `ApiResponse<T>`, `LoginInterceptor`, `WebMvcConfigurer` 등록

- [x] 6. Mustache 템플릿 작성
  `user/join.mustache`, `user/login.mustache`
