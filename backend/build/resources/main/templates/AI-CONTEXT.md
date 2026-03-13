<!-- Parent: ../AI-CONTEXT.md -->

# templates

## 목적
Mustache 서버사이드 렌더링 뷰 파일 보관. 도메인별 디렉토리로 분리.

## 하위 디렉토리
- `user/` - 회원가입, 로그인 폼
- `post/` - 게시글 목록, 상세, 작성/수정 폼 (Phase 3 예정)
- `comment/` - 댓글 수정 폼 (Phase 4 예정)
- `layout/` - 공통 헤더/푸터 파셜 (예정)

## AI 작업 지침
- 컨트롤러에서 `return "user/join"` → `templates/user/join.mustache` 매핑
- 모든 페이지는 `{{> layout/header}}`, `{{> layout/footer}}` 파셜 포함
- 본인 글/댓글 수정·삭제 버튼: `{{#isOwner}}...{{/isOwner}}` 조건부 렌더링
- 폼 제출은 항상 `method="POST"` (GET 사용 금지)
