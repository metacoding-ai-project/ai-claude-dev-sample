<!-- Parent: ../AI-CONTEXT.md -->

# backend

## 목적
Spring Boot 4.0.3 + Java 21 기반 블로그 백엔드. SSR(Mustache) + REST API 혼합 구조.

## 주요 파일
| 파일 | 설명 |
|------|------|
| `build.gradle` | Gradle 빌드 설정 (Spring Boot, JPA, Mustache, Lombok, H2, MySQL) |
| `gradle/wrapper/` | Gradle Wrapper 설정 (9.4.0) |

## 하위 디렉토리
- `src/main/java/com/example/blog/` - 애플리케이션 소스 코드
- `src/main/resources/` - 설정 파일 및 Mustache 템플릿

## AI 작업 지침
- 빌드: `./gradlew build` (gradle-wrapper.jar 필요) 또는 시스템 gradle 직접 사용
- 의존성 추가 시 버전은 Spring Boot BOM이 관리 → 버전 명시 불필요 (lombok 등)
- 새 도메인 추가 시 `src/main/java/.../domain/{도메인}/` 하위에 entity/repository/dto/service/controller 패키지 생성

## 의존성
- `spring-boot-starter-web` — MVC, REST
- `spring-boot-starter-data-jpa` — JPA/Hibernate
- `spring-boot-starter-mustache` — SSR 템플릿 엔진
- `h2` (runtimeOnly) — 개발용 인메모리 DB
- `mysql-connector-j` (runtimeOnly) — 프로덕션 DB
- `lombok` (compileOnly + annotationProcessor)
