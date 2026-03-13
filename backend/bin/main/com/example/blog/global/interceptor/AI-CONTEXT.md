<!-- Parent: ../AI-CONTEXT.md -->

# global/interceptor

## 목적
HTTP 요청 전처리 인터셉터. 현재 로그인 여부 체크 담당.

## 주요 파일
| 파일 | 설명 |
|------|------|
| `LoginInterceptor.java` | `HandlerInterceptor` 구현. 세션에 `userId` 없으면 `/login-form`으로 리다이렉트 |

## AI 작업 지침
- `request.getSession(false)` 사용 — `true` 사용 시 빈 세션이 생성되므로 금지
- 인터셉터 적용 경로는 `WebConfig`에서 관리
- 현재 Spring Security 미사용 — 인증 관련 변경 시 이 파일과 `WebConfig` 함께 수정
