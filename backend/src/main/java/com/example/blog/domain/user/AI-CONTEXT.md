<!-- Parent: ../AI-CONTEXT.md -->

# domain/user

## 목적
회원 도메인. 회원가입, 로그인, 로그아웃, username 중복 체크를 담당한다.

## 하위 디렉토리
- `entity/` - `User` JPA 엔티티
- `repository/` - `UserRepository` (JpaRepository)
- `dto/` - `JoinRequest`, `LoginRequest`
- `service/` - `UserService` (비즈니스 로직)
- `controller/` - `UserController` (SSR), `UserApiController` (REST)

## AI 작업 지침
- 비밀번호는 현재 평문 저장 (교육 목적) — 실제 암호화 추가 시 BCrypt 사용
- 세션 키: `"userId"` (Long 타입) — 다른 도메인에서 로그인 체크 시 이 키 참조
- username 중복 체크는 서비스 레이어에서 `existsByUsername()` 호출

## URL
| Method | URL | 설명 |
|--------|-----|------|
| GET | `/join-form` | 회원가입 폼 |
| POST | `/join` | 회원가입 처리 |
| GET | `/login-form` | 로그인 폼 |
| POST | `/login` | 로그인 처리 |
| GET | `/logout` | 로그아웃 |
| GET | `/api/users/check` | username 중복 체크 (AJAX) |
