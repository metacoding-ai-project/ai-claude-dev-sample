<!-- Parent: ../AI-CONTEXT.md -->

# domain/user/controller

## 주요 파일
| 파일 | 설명 |
|------|------|
| `UserController.java` | `@Controller`. SSR 뷰 반환. 회원가입/로그인/로그아웃 처리 |
| `UserApiController.java` | `@RestController`. `GET /api/users/check` — username 중복 체크 AJAX API |

## AI 작업 지침
- SSR 컨트롤러: 성공 시 `return "redirect:/..."`, 폼은 `return "user/join"` 형태
- REST 컨트롤러: 응답은 `ApiResponse<T>` 래퍼 필수
- 의존성 주입: `@RequiredArgsConstructor`
- 인증 불필요 경로이므로 인터셉터 제외 설정 이미 완료

## URL 매핑
| Controller | Method | URL | 동작 |
|-----------|--------|-----|------|
| UserController | GET | `/join-form` | `user/join` 뷰 반환 |
| UserController | POST | `/join` | 가입 처리 → `redirect:/login-form` |
| UserController | GET | `/login-form` | `user/login` 뷰 반환 |
| UserController | POST | `/login` | 로그인 처리 → `redirect:/posts` |
| UserController | GET | `/logout` | 세션 무효화 → `redirect:/posts` |
| UserApiController | GET | `/api/users/check` | `ApiResponse<Boolean>` 반환 |
