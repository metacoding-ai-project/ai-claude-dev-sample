<!-- Parent: ../AI-CONTEXT.md -->

# templates/user

## 목적
회원 관련 Mustache 뷰 파일. 회원가입 폼, 로그인 폼.

## 주요 파일
| 파일 | 설명 |
|------|------|
| `join.mustache` | 회원가입 폼 (username/password/email 입력, AJAX 중복 확인 버튼 포함) |
| `login.mustache` | 로그인 폼 (username/password 입력) |

## AI 작업 지침
- `join.mustache`: username 중복 확인은 `fetch('/api/users/check?username=...')` AJAX 호출
- 결과 메시지: `<span id="check-msg">` 에 동적 텍스트 삽입
- 폼 제출 후 리다이렉트는 컨트롤러에서 처리 (`redirect:/login-form`, `redirect:/posts`)
- 현재 CSS 없음 — 스타일 추가 시 `static/css/` 에 파일 생성 후 헤더 파셜에서 link 추가
