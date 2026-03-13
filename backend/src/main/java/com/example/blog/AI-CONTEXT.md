<!-- Parent: ../../../../../../AI-CONTEXT.md -->

# com.example.blog

## 목적
애플리케이션 최상위 패키지. 도메인 레이어와 전역 공통 코드로 구성.

## 주요 파일
| 파일 | 설명 |
|------|------|
| `BlogApplication.java` | Spring Boot 진입점 (`@SpringBootApplication`) |

## 하위 디렉토리
- `domain/` - 비즈니스 도메인별 패키지 (user, post, comment)
- `global/` - 전역 공통 코드 (dto, interceptor, config)

## AI 작업 지침
- 새 도메인 추가 시 `domain/{이름}/` 하위에 entity/repository/dto/service/controller 5개 패키지 생성
- 전역 예외 핸들러, 공통 유틸리티는 `global/` 하위에 추가
