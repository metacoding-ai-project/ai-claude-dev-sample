# Changelog

| 날짜 | 변경 항목 | 이전 | 이후 | 이유 |
|------|-----------|------|------|------|
| 2026-03-14 | 섹션9 신규: 페이징 model attributes 표준 추가 | 없음 | hasPrev/prevPage/hasNext/nextPage/pageNum/totalPages 6개 표준화 | Mustache 연산 불가 → 컨트롤러에서 미리 계산해 전달 패턴 구현에서 추출 |
| 2026-03-14 | 섹션10 신규: loginUserId 모델 패턴 추가 | 없음 | 모든 뷰에서 loginUserId를 model에 추가하는 표준 문서화 | header.mustache 로그인 분기 렌더링 구현에서 추출 |
| 2026-03-13 | 전체 초기 작성 | 빈 템플릿 | Mustache렌더링/레이아웃파셜/폼패턴/AJAX/조건부렌더링/정적파일 규칙 | 기존 mustache 파일 및 tech-stack.md에서 UI 패턴 추출 |
