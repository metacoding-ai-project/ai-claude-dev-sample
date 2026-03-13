# HTML/Mustache 구현 패턴

> design-system.md에서 코드 블록이 필요한 섹션을 참조할 때 읽는다.

---

## 레이아웃 파셜

모든 페이지에서 공통 헤더/푸터를 include:

```html
{{> layout/header}}

<!-- 페이지 본문 -->

{{> layout/footer}}
```

---

## 폼 패턴

```html
<form action="/join" method="post">
    <input type="text" name="username" required>
    <input type="password" name="password" required>
    <button type="submit">가입하기</button>
</form>
```

---

## AJAX 패턴 (fetch + ApiResponse)

```html
<span id="check-msg"></span>
<script>
    fetch('/api/users/check?username=' + encodeURIComponent(username))
        .then(res => res.json())
        .then(json => {
            document.getElementById('check-msg').textContent =
                json.data ? '이미 사용 중인 아이디입니다.' : '사용 가능한 아이디입니다.';
        });
</script>
```

---

## 조건부 렌더링 (isOwner 분기)

```html
{{#isOwner}}
    <a href="/posts/{{id}}/edit-form">수정</a>
    <form action="/posts/{{id}}/delete" method="post">
        <button type="submit">삭제</button>
    </form>
{{/isOwner}}
```
