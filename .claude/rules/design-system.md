# Design System

> 코드베이스에서 추출한 UI 표준. 새 페이지·컴포넌트 작성 시 이 패턴을 따른다.

---

## 1. 렌더링 방식

- 서버사이드 렌더링: Mustache (`.mustache` 확장자)
- 파일 위치: `src/main/resources/templates/{도메인}/{기능}.mustache`
- 뷰 반환 예: `return "user/join"` → `templates/user/join.mustache`

---

## 2. 레이아웃 파셜

- 공통 헤더: `layout/header.mustache` — 모든 페이지에서 `{{> layout/header}}` 포함
- 공통 푸터: `layout/footer.mustache` — 모든 페이지에서 `{{> layout/footer}}` 포함

> 상세 패턴: `.claude/reference/html-patterns.md`

---

## 3. 폼 패턴

- 폼 제출: `<form method="POST" action="/...">`
- HTML form은 GET/POST만 지원 — 수정/삭제도 POST 사용
- 필드 바인딩: `name` 속성을 DTO 필드명과 일치시킴

> 상세 패턴: `.claude/reference/html-patterns.md`

---

## 4. AJAX 패턴 (Vanilla JS)

- 별도 프레임워크 없음 — `fetch()` 사용
- API 응답 구조: `{ status, msg, data }`
- 중복 확인 등 단순 피드백은 인라인 `<span>` 으로 표시

> 상세 패턴: `.claude/reference/html-patterns.md`

---

## 5. 조건부 렌더링 (권한 분기)

- 본인 글/댓글에만 수정·삭제 버튼 노출
- Mustache `{{#isOwner}}...{{/isOwner}}` 패턴 사용
- 모델에 `isOwner` 플래그를 서비스/컨트롤러에서 계산해 전달

> 상세 패턴: `.claude/reference/html-patterns.md`

---

## 6. 페이지 간 링크

- 로그인 ↔ 회원가입 상호 링크 제공
- 형식: `<a href="/login-form">이미 계정이 있으신가요? 로그인</a>`

---

## 7. 정적 파일

- CSS: `src/main/resources/static/css/`
- JS: `src/main/resources/static/js/`
- 인터셉터 제외 경로: `/css/**`, `/js/**`

---

## 9. 페이징 model attributes 표준

컨트롤러에서 페이징 관련 model attribute를 아래 이름으로 통일한다 (Mustache에서 연산 불가이므로 모두 미리 계산):

| attribute | 타입 | 설명 |
|-----------|------|------|
| `hasPrev` | boolean | 이전 페이지 존재 여부 |
| `prevPage` | int | 이전 페이지 번호 (0-based) |
| `hasNext` | boolean | 다음 페이지 존재 여부 |
| `nextPage` | int | 다음 페이지 번호 (0-based) |
| `pageNum` | int | 표시용 페이지 번호 (1-based, `page + 1`) |
| `totalPages` | int | 전체 페이지 수 |

## 10. loginUserId 모델 패턴

모든 뷰에서 네비게이션 로그인 상태 분기가 필요하므로 컨트롤러 각 메서드에서 `loginUserId`를 model에 추가한다.
header.mustache에서 `{{#loginUserId}}` / `{{^loginUserId}}`로 로그인/비로그인 분기 렌더링.

> 상세 패턴: `.claude/reference/java-patterns.md`

## 8. 도메인별 템플릿 디렉토리

| 도메인 | 경로 |
|--------|------|
| 회원 | `templates/user/` |
| 게시글 | `templates/post/` |
| 댓글 | `templates/comment/` |
| 레이아웃 | `templates/layout/` |

> 변경 이력: .claude/logs/design-system-changelog.md
