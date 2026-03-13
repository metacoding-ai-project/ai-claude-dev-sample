# 기술 명세서: 멀티유저 블로그 플랫폼

---

## 1. 전체 스택 요약

| 분류 | 기술 |
|------|------|
| Language | Java |
| Framework | Spring Boot |
| Build Tool | Gradle |
| Template Engine | Mustache |
| Frontend | HTML / CSS / JS |
| ORM | Spring Data JPA (Hibernate) |
| DB (개발) | H2 (인메모리) |
| DB (프로덕션) | MySQL |
| 인증 | HttpSession + Interceptor (Spring Security 미사용) |

---

## 2. Backend

### Spring Boot
- 웹 레이어: `@Controller`, `@GetMapping`, `@PostMapping`
- 리다이렉트 방식: `redirect:/...`
- 요청 파라미터 바인딩: `@ModelAttribute`, `@RequestParam`
- API 레이어: `@RestController` (AJAX 통신용)

### API 컨트롤러
- `UserApiController` — `@RestController`, username 중복 체크
  - `GET /api/users/check?username=` → `ApiResponse<Boolean>` 반환

### 공통 응답 DTO
```java
// global/dto/ApiResponse.java
public class ApiResponse<T> {
    private int status;   // HTTP 상태 코드
    private String msg;   // 메시지
    private T data;       // 응답 데이터
}
```

### Spring Data JPA
- 엔티티: `@Entity`, `@Id`, `@GeneratedValue`, `@ManyToOne`
- 리포지토리: `JpaRepository` 상속
- 페이징: `Pageable`, `Page<T>`
- 검색: `findByTitleContaining(keyword, pageable)`

### 인증 (세션 기반)
- 로그인 시 `HttpSession`에 사용자 정보 저장
- `HandlerInterceptor` 구현체로 로그인 여부 체크
- 권한 체크 (본인 여부)는 서비스 레이어에서 처리

---

## 3. Database

### 개발 환경 (H2) — `application-dev.properties`
- 인메모리 모드
- H2 Console 활성화 (`/h2-console`)
- `spring.jpa.hibernate.ddl-auto=create-drop`

### 프로덕션 환경 (MySQL) — `application-prod.properties`
- `spring.jpa.hibernate.ddl-auto=validate` 또는 `none`

### 공통 — `application.properties`
- `spring.profiles.active=dev` (기본값, 배포 시 `prod`로 변경)

---

## 4. Frontend

### Mustache
- 서버사이드 렌더링
- 레이아웃 공통화: `header`, `footer` 파셜 분리
- 조건부 렌더링으로 본인 글/댓글에만 수정·삭제 버튼 노출

### HTML / CSS / JS
- 별도 프레임워크 없음 (Vanilla JS)
- 폼 제출 방식: `<form method="POST">`

---

## 5. 프로젝트 구조

```
src/
└── main/
    ├── java/
    │   └── com/example/blog/
    │       ├── domain/
    │       │   ├── user/
    │       │   │   ├── User.java
    │       │   │   ├── UserRepository.java
    │       │   │   ├── UserService.java
    │       │   │   ├── UserController.java
    │       │   │   └── UserApiController.java
    │       │   ├── post/
    │       │   │   ├── Post.java
    │       │   │   ├── PostRepository.java
    │       │   │   ├── PostService.java
    │       │   │   └── PostController.java
    │       │   └── comment/
    │       │       ├── Comment.java
    │       │       ├── CommentRepository.java
    │       │       ├── CommentService.java
    │       │       └── CommentController.java
    │       └── global/
    │           ├── dto/
    │           │   └── ApiResponse.java
    │           └── interceptor/
    │               └── LoginInterceptor.java
    └── resources/
        ├── templates/
        │   ├── layout/
        │   │   ├── header.mustache
        │   │   └── footer.mustache
        │   ├── user/
        │   ├── post/
        │   └── comment/
        ├── static/
        │   ├── css/
        │   └── js/
        ├── application.properties
        ├── application-dev.properties
        └── application-prod.properties
```

---

## 6. 의존성 (build.gradle)

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-mustache'
    runtimeOnly 'com.h2database:h2'
    runtimeOnly 'com.mysql:mysql-connector-j'
}
```
