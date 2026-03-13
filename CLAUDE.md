세션 시작 시 이 파일을 읽고 아래 지침을 따른다.

---
## rules / reference 분리 규칙

`.claude/rules/`는 매 세션 자동 로드 — **항상 짧아야 한다.**
`.claude/reference/`는 필요 시 명시적으로 읽는다.

### rules/ 작성 기준
- 섹션당 최대 20줄 / **코드 블록 금지** (인라인 클래스명은 예외)
- 코드 예시·전체 구현 패턴이 필요하면 reference/ 파일로 추출하고 포인터만 남긴다
- 포인터 형식: `> 상세 패턴: .claude/reference/{파일명}.md`

### reference/ 작성 기준
- 파일명: `{도메인}-patterns.md` (예: `md-patterns.md`, `table-patterns.md`)
- 파일 상단에 "언제 이 파일을 읽어야 하는지" 한 줄 설명 필수
- 추가 시 `.claude/reference/index.md` 인덱스에 행 등록

### 추출 판단 기준
| 내용 | 위치 |
|------|------|
| 규칙·제약·금지사항·선택 기준 | `rules/` |
| 비즈니스 도메인 로직 (항상 참조) | `rules/` |
| 코드 블록이 포함된 구현 패턴 | `reference/` |
| 20줄 초과 섹션 | `reference/` 추출 |

---
## AI-CONTEXT

코드 작업 전 해당 디렉토리에 `AI-CONTEXT.md`가 있으면 먼저 읽어라.
`/deepinit`을 실행하면 생성되며, 디렉토리별 구조·의존성·핵심 로직을 요약한다.

---
## Plan 저장 규칙

플랜 모드에서 승인(ExitPlanMode) 후 구현 완료 시, 플랜 내용을 아래 경로에 보고서로 **반드시 저장**한다:
- 경로: `.person/reports/{날짜}/{기능명}-report.md`
- 날짜 형식: `YYYY-MM-DD`
- 기능명: 플랜 제목에서 추출 (kebab-case)

---
