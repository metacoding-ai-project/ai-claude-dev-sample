<!-- Parent: ../AI-CONTEXT.md -->

# global/dto

## 목적
API 공통 응답 래퍼 DTO.

## 주요 파일
| 파일 | 설명 |
|------|------|
| `ApiResponse.java` | 제네릭 응답 래퍼. `status(int)`, `msg(String)`, `data(T)` 필드. 팩토리 메서드 `ok()`, `error()` 제공 |

## AI 작업 지침
- 모든 `@RestController` 응답은 `ApiResponse<T>`로 감싼다
- 성공: `ApiResponse.ok(data)` → `{ status:200, msg:"ok", data:... }`
- 실패: `ApiResponse.error(msg)` → `{ status:400, msg:"...", data:null }`
- 현재 Lombok 미적용 (`ApiResponse`는 final 필드 + 수동 getter) — 변경 시 `@Getter` 추가 가능
