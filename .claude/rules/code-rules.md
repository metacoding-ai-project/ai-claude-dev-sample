# 코드 컨벤션

> 코드베이스에서 추출한 공통된 코드 표준. 코드 작성 시 이 패턴을 따른다.

---

## 1. 패키지 구조

- 도메인별로 entity/repository/dto/service/controller 패키지를 분리한다.
- 전역 공통 코드는 `global/` 하위에 둔다 (dto, interceptor, config).

> 상세 패턴: `.claude/reference/java-patterns.md`

---

## 2. 엔티티 (Entity)

- `@Entity`, `@Table(name = "테이블명")` 필수
- Lombok: `@Getter`, `@NoArgsConstructor(access = AccessLevel.PROTECTED)`
- 기본 생성자는 `protected` — JPA 스펙 + 직접 생성 방지
- 엔티티에 `@Setter` 금지 — 필드는 생성자로만 초기화
- PK: `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)`
- FK 관계: `@ManyToOne(fetch = FetchType.LAZY)`

> 상세 패턴: `.claude/reference/java-patterns.md`

---

## 3. 리포지토리 (Repository)

- `JpaRepository<Entity, Long>` 상속
- 커스텀 쿼리는 도메인 의도를 드러내는 메서드명 사용
  - `findByUsername(String username)` → `Optional<User>`
  - `existsByUsername(String username)` → `boolean`
  - `findByTitleContaining(String keyword, Pageable pageable)` → `Page<Post>`

---

## 4. DTO

- 요청 DTO (`XxxRequest`): `@Getter @Setter` — Spring MVC 바인딩 필요
- 응답 DTO: `@Getter` + 불변 필드(`final`) + 생성자
- 요청/응답 DTO는 반드시 분리 (엔티티를 직접 반환하지 않는다)
- 필드명은 HTML `input name` 속성과 일치시킨다
- 소유권 판단이 필요한 응답 DTO는 연관 엔티티의 `userId`(Long)를 포함한다 — 컨트롤러에서 `loginUserId.equals(dto.getUserId())`로 `isOwner` 계산

---

## 5. 서비스 (Service)

- `@Service`
- 클래스 레벨: `@Transactional(readOnly = true)` (읽기 최적화)
- 쓰기 메서드 (insert/update/delete): `@Transactional` 명시
- 의존성 주입: `@RequiredArgsConstructor` + `private final` 필드
- 비즈니스 규칙 위반: `IllegalArgumentException` 사용
- 권한 체크 (본인 여부)는 서비스 레이어에서 처리
- `import org.springframework.transaction.annotation.Transactional` 사용 (`jakarta` 아님)

> 상세 패턴: `.claude/reference/java-patterns.md`

---

## 6. 컨트롤러 (Controller)

- SSR(뷰 반환): `@Controller`
- API(JSON 반환): `@RestController` + `@RequestMapping("/api/...")`
- 요청 바인딩: `@ModelAttribute` 생략 가능 (스프링 자동 변환)
- 리다이렉트: `return "redirect:/..."`
- 뷰 반환: `return "도메인/파일명"` (`.mustache` 생략)
- 의존성 주입: `@RequiredArgsConstructor` + `private final`

---

## 7. 공통 응답 DTO (ApiResponse)

- 모든 API 응답은 `ApiResponse<T>` 래퍼 사용
- 팩토리 메서드: `ApiResponse.ok(data)`, `ApiResponse.error(msg)`
- 필드: `status(int)`, `msg(String)`, `data(T)`

---

## 8. 인증 / 세션

- 로그인 시 `session.setAttribute("userId", user.getId())`
- 로그인 체크: `LoginInterceptor` (HandlerInterceptor 구현)
- `request.getSession(false)` — 세션 없으면 null 반환
- 인터셉터 등록: `WebConfig`에서 제외 경로 명시
- `excludePathPatterns`에 와일드카드(`/posts/*`)로 일괄 제외하면 `/posts/new-form`도 풀린다 — 필요한 경로만 명시적으로 추가
- 비로그인 허용이지만 AntPattern으로 표현하기 어려운 경로(`/posts/{숫자}`)는 `LoginInterceptor.preHandle()`에서 HTTP 메서드 + `URI.matches()` 조합으로 처리

> 상세 패턴: `.claude/reference/java-patterns.md`

---

## 9. Lombok 사용 규칙

| 적용 위치 | 사용 어노테이션 |
|-----------|----------------|
| 엔티티 | `@Getter`, `@NoArgsConstructor(access = PROTECTED)` |
| 요청 DTO | `@Getter`, `@Setter` |
| 응답 DTO | `@Getter` |
| Service/Controller | `@RequiredArgsConstructor` |

---

## 10. DB 환경 분리

- `application.properties`: `spring.profiles.active=dev`
- `application-dev.properties`: H2 인메모리, `ddl-auto=create-drop`, `show-sql=true`
- `application-prod.properties`: MySQL, `ddl-auto=validate`, `show-sql=false`, 자격증명은 환경변수(`${DB_USERNAME}`, `${DB_PASSWORD}`)

## 11. Mustache 컨텍스트 제한 대응

Mustache는 루프 내부에서 상위 컨텍스트(`{{../parentField}}`)를 지원하지 않는다.
루프 아이템에 상위 데이터(postId, isOwner 등)가 필요하면 컨트롤러에서 `Map<String, Object>` 리스트로 구성해 전달한다.

> 상세 패턴: `.claude/reference/mustache-patterns.md`

## 12. rules/ 파일 작성·수정 후 검증 의무

rules/ 파일에 내용을 추가하거나 수정한 뒤 반드시 확인한다:
1. 코드 블록(` ``` `)이 포함되어 있으면 reference/로 추출하고 포인터로 교체한다
2. reference/로 이동한 내용은 `.claude/reference/index.md`(또는 `README.md`)에 행을 추가한다
3. 인라인 코드(`` ` `` 1개)는 허용 — 펜스 코드 블록(` ``` `)만 금지

> 변경 이력: .claude/logs/code-rules-changelog.md
