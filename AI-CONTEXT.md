# ai_labb

## 목적
멀티유저 블로그 플랫폼 프로젝트 루트. Spring Boot 백엔드 단일 모듈 구조.

## 주요 파일
| 파일/디렉토리 | 설명 |
|--------------|------|
| `backend/` | Spring Boot 애플리케이션 전체 |
| `.claude/` | AI 작업 가이드 (rules, skills, logs) |
| `.person/` | 프로젝트 문서 (prd, spec, erd, phases) |
| `CLAUDE.md` | Claude 작업 지침 및 체크리스트 |

## 하위 디렉토리
- `backend/` - Spring Boot 4.0.3 + Java 21 백엔드
- `.claude/rules/` - 코드 컨벤션, 비즈니스 규칙, 디자인 시스템
- `.person/docs/` - ERD, 기능 명세, 기술 명세, 개발 단계

## AI 작업 지침
- 소스 코드 작업은 항상 `backend/` 하위에서만 수행한다
- 규칙 파일은 `.claude/rules/` 참조: `code-rules.md`, `business-rules.md`, `design-system.md`
- 기능 명세는 `.person/docs/spec.md`, ERD는 `.person/docs/erd.md` 참조
- 개발 단계 현황은 `.person/docs/phases.md` 참조

## 현재 단계
Phase 2 (인증) 구현 완료 → Phase 3 (게시글) 예정
