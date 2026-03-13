# Mustache 패턴 레퍼런스

## 상위 컨텍스트 접근 불가 대응

Mustache는 루프 내부에서 상위 컨텍스트(`{{../parentField}}`)를 지원하지 않는다.
루프 아이템에 상위 데이터(postId, isOwner 등)가 필요한 경우 컨트롤러에서
`Map<String, Object>` 리스트로 구성한다.

```java
List<Map<String, Object>> commentList = comments.stream().map(c -> {
    Map<String, Object> map = new LinkedHashMap<>();
    map.put("id", c.getId());
    map.put("content", c.getContent());
    map.put("username", c.getUsername());
    map.put("isOwner", loginUserId != null && loginUserId.equals(c.getUserId()));
    map.put("postId", postId);   // 상위 컨텍스트 값을 아이템에 복사
    return map;
}).collect(Collectors.toList());
model.addAttribute("comments", commentList);
```

템플릿에서는 `{{postId}}`, `{{isOwner}}`를 루프 내에서 바로 사용:

```html
{{#comments}}
<div>
    <span>{{username}}: {{content}}</span>
    {{#isOwner}}
    <a href="/posts/{{postId}}/comments/{{id}}/edit-form">수정</a>
    {{/isOwner}}
</div>
{{/comments}}
```

## 페이징 model attributes 표준

Mustache에서는 `page - 1` 같은 연산이 불가능하므로 컨트롤러에서 미리 계산해 전달.

```java
model.addAttribute("hasPrev", page > 0);
model.addAttribute("prevPage", page - 1);
model.addAttribute("hasNext", page < posts.getTotalPages() - 1);
model.addAttribute("nextPage", page + 1);
model.addAttribute("pageNum", page + 1);      // 1-based 표시용
model.addAttribute("totalPages", posts.getTotalPages());
```
