-- users (password 평문)
INSERT INTO users (username, password, email) VALUES ('ssar', '1234', 'ssar@example.com');
INSERT INTO users (username, password, email) VALUES ('bob', 'password123', 'bob@example.com');
INSERT INTO users (username, password, email) VALUES ('charlie', 'password123', 'charlie@example.com');

-- posts
INSERT INTO posts (title, content, image, user_id) VALUES ('Spring Boot 시작하기', 'Spring Boot는 스프링 기반 애플리케이션을 빠르게 만들 수 있게 해주는 프레임워크입니다. 자동 설정과 의존성 관리 덕분에 개발자는 비즈니스 로직에 집중할 수 있습니다.', NULL, 1);
INSERT INTO posts (title, content, image, user_id) VALUES ('JPA와 Hibernate 기초', 'JPA는 자바 ORM 표준 인터페이스이고, Hibernate는 그 구현체입니다. 엔티티 매핑과 JPQL을 이용해 데이터베이스를 객체지향적으로 다룰 수 있습니다.', NULL, 1);
INSERT INTO posts (title, content, image, user_id) VALUES ('Mustache 템플릿 엔진', 'Mustache는 로직 없는 템플릿 엔진입니다. 서버와 클라이언트 모두에서 사용할 수 있으며, 간단한 문법 덕분에 빠르게 익힐 수 있습니다.', NULL, 2);
INSERT INTO posts (title, content, image, user_id) VALUES ('HTTP 세션과 인증', 'HttpSession을 이용한 간단한 인증 구현 방법입니다. 로그인 시 userId를 세션에 저장하고, 인터셉터에서 세션 유무를 체크합니다.', NULL, 2);
INSERT INTO posts (title, content, image, user_id) VALUES ('H2 인메모리 데이터베이스', 'H2는 자바로 작성된 경량 인메모리 DB입니다. 개발 환경에서 별도 설치 없이 빠르게 사용할 수 있어 테스트와 프로토타입에 적합합니다.', NULL, 3);
INSERT INTO posts (title, content, image, user_id) VALUES ('Lombok 활용법', 'Lombok은 반복적인 보일러플레이트 코드를 어노테이션으로 자동 생성해줍니다. @Getter, @RequiredArgsConstructor 등을 활용해 코드를 간결하게 유지하세요.', NULL, 3);
INSERT INTO posts (title, content, image, user_id) VALUES ('RESTful API 설계 원칙', 'REST는 HTTP를 기반으로 자원을 표현하는 아키텍처 스타일입니다. 명확한 URL 설계와 HTTP 메서드 활용으로 직관적인 API를 만들 수 있습니다.', NULL, 1);
INSERT INTO posts (title, content, image, user_id) VALUES ('페이징 처리 구현', 'Spring Data JPA의 Pageable을 이용하면 페이징을 손쉽게 구현할 수 있습니다. Page 객체에서 totalPages, hasNext 등 유용한 정보를 얻을 수 있습니다.', NULL, 2);
INSERT INTO posts (title, content, image, user_id) VALUES ('인터셉터로 접근 제어', 'HandlerInterceptor를 구현해 로그인 체크 로직을 한 곳에서 관리합니다. preHandle 메서드에서 세션을 확인하고 미인증 요청을 차단합니다.', NULL, 3);
INSERT INTO posts (title, content, image, user_id) VALUES ('빌드 도구: Gradle vs Maven', 'Gradle은 Groovy/Kotlin DSL 기반의 유연한 빌드 도구이고, Maven은 XML 기반의 전통적인 빌드 도구입니다. Spring Boot 프로젝트에서는 둘 다 널리 사용됩니다.', NULL, 1);

-- comments
INSERT INTO comments (content, post_id, user_id) VALUES ('정말 유익한 글이네요! Spring Boot 덕분에 많이 배웠습니다.', 1, 2);
INSERT INTO comments (content, post_id, user_id) VALUES ('저도 처음 접할 때 많이 헤맸는데, 이 글이 있었다면 훨씬 쉬웠을 것 같아요.', 1, 3);
INSERT INTO comments (content, post_id, user_id) VALUES ('JPA 처음 배울 때 N+1 문제로 고생했는데, 이 글에서도 다뤄주시면 좋겠어요.', 2, 2);
INSERT INTO comments (content, post_id, user_id) VALUES ('Hibernate 말고 다른 JPA 구현체도 있나요?', 2, 3);
INSERT INTO comments (content, post_id, user_id) VALUES ('Mustache가 Thymeleaf보다 가볍게 느껴지더라고요.', 3, 1);
INSERT INTO comments (content, post_id, user_id) VALUES ('로직 없는 템플릿이라 처음엔 불편했는데 익숙해지니 오히려 좋네요.', 3, 3);
INSERT INTO comments (content, post_id, user_id) VALUES ('세션 방식 말고 JWT도 써보셨나요?', 4, 1);
INSERT INTO comments (content, post_id, user_id) VALUES ('H2 콘솔로 직접 데이터 보는 게 정말 편리해요.', 5, 1);
INSERT INTO comments (content, post_id, user_id) VALUES ('프로덕션에서는 역시 MySQL이나 PostgreSQL 써야겠죠?', 5, 2);
INSERT INTO comments (content, post_id, user_id) VALUES ('@Builder 써보신 분 계신가요? 엔티티에는 안 쓰는 게 맞죠?', 6, 1);
