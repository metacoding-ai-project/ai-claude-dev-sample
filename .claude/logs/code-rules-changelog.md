# code-rules changelog

| 날짜 | 변경 항목 | 이전 | 이후 | 이유 |
|------|----------|------|------|------|
| 2026-03-14 | 재발방지: rules/ 코드 블록 인라인 + 포인터 누락 | 규칙 없음 | rules/ 수정 후 코드 블록 제거·reference 추출·포인터 등록 검증 의무화 | 규칙 모호 — 분리 기준은 CLAUDE.md에 있었으나 작성 후 검증 절차 없었음 |
| 2026-03-14 | 섹션4 응답 DTO userId 포함 패턴 추가 | 없음 | 소유권 판단용 userId 필드 응답 DTO에 포함 규칙 추가 | PostResponse.userId, CommentResponse.userId 패턴 구현에서 추출 |
| 2026-03-14 | 섹션8 LoginInterceptor URI 매칭 패턴 추가 | excludePathPatterns만 언급 | GET + URI.matches()로 세밀한 예외 처리 패턴 문서화 | /posts/{id} 비로그인 허용을 AntPattern 없이 구현한 패턴 |
| 2026-03-14 | 섹션11 신규: Mustache 상위 컨텍스트 접근 대응 | 없음 | Map<String,Object> 리스트로 루프 아이템에 상위 데이터 포함 패턴 | Mustache 루프 내 ../parentField 미지원으로 인한 isOwner, postId 주입 패턴 |
| 2026-03-13 | 전체 초기 작성 | 빈 템플릿 | 패키지구조/엔티티/리포지토리/DTO/서비스/컨트롤러/Lombok/DB환경분리 규칙 | 코드베이스 및 docs에서 규칙 추출 |
| 2026-03-13 | 재발방지: TODO 완료 후 체크박스 미업데이트 | 규칙 없음 | TODO 항목 완료 시 `[x]` 업데이트 의무화 | 규칙 누락 — 작업 완료 후 TODO.md 갱신 절차 없었음 |
| 2026-03-13 | 재발방지: 잘못된 경로에 파일 생성 | 규칙 없음 | 생성 전 경로 확인, 서브폴더 격리 지시 시 루트 생성 금지, 생성 후 루트 검증 의무화 | 지시 오독 — `backend/` 격리 지시를 무시하고 루트에 Gradle 파일 생성 후 미삭제 |
