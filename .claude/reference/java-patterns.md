# Java 구현 패턴

> code-rules.md에서 코드 블록이 필요한 섹션을 참조할 때 읽는다.

---

## 패키지 구조

```
com.example.blog/
├── domain/
│   └── {도메인}/          # user, post, comment
│       ├── entity/
│       ├── repository/
│       ├── dto/
│       ├── service/
│       └── controller/
└── global/
    ├── dto/               # ApiResponse
    ├── interceptor/       # LoginInterceptor
    └── config/            # WebConfig
```

---

## 엔티티 패턴

```java
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public User(String username, ...) { ... }  // 모든 필드 초기화
}
```

---

## 서비스 패턴

```java
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class XxxService {
    private final XxxRepository xxxRepository;

    @Transactional
    public void save(...) { ... }  // 쓰기에만 @Transactional 추가
}
```

---

## 인증/세션 패턴 (URI 정규식)

AntPattern으로 표현하기 어려운 경로는 `LoginInterceptor.preHandle()`에서 처리:

```java
if ("GET".equals(request.getMethod()) && request.getRequestURI().matches("/posts/\\d+")) return true;
```

---

## loginUserId 모델 패턴

모든 뷰에서 네비게이션 로그인 상태 분기가 필요하므로 컨트롤러 각 메서드에서:

```java
model.addAttribute("loginUserId", session.getAttribute("userId"));
```

header.mustache에서 `{{#loginUserId}}` / `{{^loginUserId}}`로 로그인/비로그인 분기 렌더링.
