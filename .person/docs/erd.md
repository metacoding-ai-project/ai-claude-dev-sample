# ERD: 멀티유저 블로그 플랫폼

---
## 1. users

| 컬럼명 | 타입 | 제약조건 | 설명 |
|--------|------|----------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 기본키 |
| username | VARCHAR(50) | NOT NULL, UNIQUE | 로그인 아이디 |
| password | VARCHAR(255) | NOT NULL | 비밀번호 (암호화) |
| email | VARCHAR(100) | NOT NULL, UNIQUE | 이메일 |

---

## 2. posts

| 컬럼명 | 타입 | 제약조건 | 설명 |
|--------|------|----------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 기본키 |
| title | VARCHAR(255) | NOT NULL | 제목 |
| content | TEXT | NOT NULL | 본문 |
| image | VARCHAR(500) | NULL | 대표 이미지 경로 |
| user_id | BIGINT | FK → users.id, NOT NULL | 작성자 |

---

## 3. comments

| 컬럼명 | 타입 | 제약조건 | 설명 |
|--------|------|----------|------|
| id | BIGINT | PK, AUTO_INCREMENT | 기본키 |
| content | VARCHAR(1000) | NOT NULL | 댓글 내용 |
| post_id | BIGINT | FK → posts.id, NOT NULL | 게시글 |
| user_id | BIGINT | FK → users.id, NOT NULL | 작성자 |

---

## 관계 요약

| 관계 | 설명 |
|------|------|
| users → posts | 1:N (한 유저가 여러 글 작성) |
| users → comments | 1:N (한 유저가 여러 댓글 작성) |
| posts → comments | 1:N (한 글에 여러 댓글) |
