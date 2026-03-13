<!-- Parent: ../AI-CONTEXT.md -->

# domain/user/dto

## 주요 파일
| 파일 | 설명 |
|------|------|
| `JoinRequest.java` | 회원가입 요청 DTO. `username`, `password`, `email`. `@Getter @Setter` |
| `LoginRequest.java` | 로그인 요청 DTO. `username`, `password`. `@Getter @Setter` |

## AI 작업 지침
- 요청 DTO는 `@Getter @Setter` 필수 (Spring MVC 폼 바인딩)
- 필드명은 HTML `input name` 속성과 반드시 일치
- 응답 DTO 추가 시 `XxxResponse` 네이밍, `@Getter`만 적용
