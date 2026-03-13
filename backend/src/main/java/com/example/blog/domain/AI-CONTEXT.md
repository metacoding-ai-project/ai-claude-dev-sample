<!-- Parent: ../AI-CONTEXT.md -->

# domain

## 목적
비즈니스 도메인별 패키지 모음. 각 도메인은 독립적인 entity/repository/dto/service/controller 구조를 갖는다.

## 하위 디렉토리
- `user/` - 회원가입, 로그인, 세션 인증 (Phase 2 완료)
- `post/` - 게시글 CRUD, 페이징, 검색 (Phase 3 예정)
- `comment/` - 댓글 CRUD (Phase 4 예정)

## AI 작업 지침
- 도메인 간 의존 금지: `user` 도메인이 `post` 도메인을 직접 import하지 않는다
- post/comment는 현재 `.gitkeep`만 있는 빈 디렉토리 — Phase 3/4에서 구현
- 각 도메인 하위에 `AI-CONTEXT.md` 참조
