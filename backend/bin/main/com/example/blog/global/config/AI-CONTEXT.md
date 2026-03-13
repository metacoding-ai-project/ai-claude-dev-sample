<!-- Parent: ../AI-CONTEXT.md -->

# global/config

## 목적
Spring MVC 설정. 인터셉터 등록 및 경로 패턴 관리.

## 주요 파일
| 파일 | 설명 |
|------|------|
| `WebConfig.java` | `WebMvcConfigurer` 구현체. `LoginInterceptor`를 `/**`에 등록하고 공개 경로 제외 |

## AI 작업 지침
- 새 공개 경로 추가 시 `excludePathPatterns()`에 추가
- 현재 제외 경로: `/`, `/join-form`, `/join`, `/login-form`, `/login`, `/posts`, `/posts/*`, `/h2-console/**`, `/css/**`, `/js/**`
- 새 인터셉터 추가 시 이 파일에서 `addInterceptor()` 체인 추가

## 의존성
- 내부: `global/interceptor/LoginInterceptor`
