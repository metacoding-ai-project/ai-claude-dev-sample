<!-- Parent: ../../../AI-CONTEXT.md -->

# resources

## 목적
Spring Boot 설정 파일 및 Mustache 뷰 템플릿 보관.

## 주요 파일
| 파일 | 설명 |
|------|------|
| `application.properties` | 공통 설정 (`spring.profiles.active=dev`) |
| `application-dev.properties` | 개발 환경: H2 인메모리, `ddl-auto=create-drop`, H2 Console 활성화 |
| `application-prod.properties` | 프로덕션: MySQL, `ddl-auto=validate`, 자격증명은 환경변수 |

## 하위 디렉토리
- `templates/` - Mustache 뷰 파일 (`{도메인}/{기능}.mustache`)

## AI 작업 지침
- 프로파일 전환: `application.properties`의 `spring.profiles.active` 값 변경
- 프로덕션 DB 자격증명은 `${DB_USERNAME}`, `${DB_PASSWORD}` 환경변수로 주입
- 새 설정 추가 시 dev/prod 양쪽에 추가한다
