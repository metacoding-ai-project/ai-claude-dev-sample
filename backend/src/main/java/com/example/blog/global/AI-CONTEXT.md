<!-- Parent: ../AI-CONTEXT.md -->

# global

## 목적
도메인에 속하지 않는 전역 공통 코드. 모든 도메인이 공유하는 DTO, 인터셉터, 설정 보관.

## 주요 파일
없음 (하위 패키지에 분산)

## 하위 디렉토리
- `dto/` - 공통 응답 래퍼 (`ApiResponse<T>`)
- `interceptor/` - 로그인 체크 인터셉터 (`LoginInterceptor`)
- `config/` - Spring MVC 설정 (`WebConfig`)

## AI 작업 지침
- 새 공통 예외 클래스, 유틸리티는 이 패키지에 추가
- 전역 예외 핸들러(`@ControllerAdvice`) 추가 시 `global/exception/` 패키지 생성
