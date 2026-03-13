<!-- Parent: ../AI-CONTEXT.md -->

# domain/user/entity

## 주요 파일
| 파일 | 설명 |
|------|------|
| `User.java` | 회원 JPA 엔티티. `id`, `username`(UNIQUE), `password`, `email`(UNIQUE) |

## AI 작업 지침
- `@Getter`, `@NoArgsConstructor(access = PROTECTED)` Lombok 적용
- 필드 추가 시 all-args 생성자 `User(username, password, email, ...)` 도 수정
- `@Setter` 추가 금지 — 불변 엔티티 원칙 유지
- 테이블명: `users` (`@Table(name = "users")`)
