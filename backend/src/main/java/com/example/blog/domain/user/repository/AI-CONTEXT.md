<!-- Parent: ../AI-CONTEXT.md -->

# domain/user/repository

## 주요 파일
| 파일 | 설명 |
|------|------|
| `UserRepository.java` | `JpaRepository<User, Long>` 상속. `findByUsername`, `existsByUsername` 커스텀 쿼리 |

## AI 작업 지침
- 커스텀 쿼리는 Spring Data 메서드 네이밍 규칙으로 추가 (`findBy...`, `existsBy...`)
- 복잡한 쿼리만 `@Query` 사용
