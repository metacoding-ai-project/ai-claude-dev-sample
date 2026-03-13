<!-- Parent: ../AI-CONTEXT.md -->

# domain/user/service

## 주요 파일
| 파일 | 설명 |
|------|------|
| `UserService.java` | 회원가입, 로그인, username 중복 체크 비즈니스 로직 |

## AI 작업 지침
- 클래스 레벨 `@Transactional(readOnly = true)` — 읽기 최적화
- 쓰기 메서드(`join`)에만 `@Transactional` 추가
- `import org.springframework.transaction.annotation.Transactional` (jakarta 아님)
- 의존성 주입: `@RequiredArgsConstructor`
- 예외: `IllegalArgumentException` (비즈니스 규칙 위반)
- 권한 체크(본인 여부)는 서비스 레이어 책임

## 현재 메서드
| 메서드 | 트랜잭션 | 설명 |
|--------|----------|------|
| `join(JoinRequest)` | `@Transactional` | username 중복 체크 후 저장 |
| `login(LoginRequest, HttpSession)` | readOnly | 비밀번호 검증 후 세션 저장 |
| `isUsernameTaken(String)` | readOnly | username 중복 여부 반환 |
