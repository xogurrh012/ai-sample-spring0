# 프로젝트 코드 컨벤션

## 1. 패키지 구조

도메인 단위의 **플랫 패키지 구조**를 사용한다.
레이어(layer) 기준이 아닌 도메인(domain) 기준으로 폴더를 구성한다.

```
com.example.demo/
├── DemoApplication.java
├── _core/                  # 공통 유틸 (도메인 무관)
│   └── utils/
│       └── Resp.java
├── board/                  # 게시글 도메인
│   ├── Board.java
│   ├── BoardController.java
│   ├── BoardService.java
│   ├── BoardRepository.java
│   ├── BoardRequest.java
│   ├── BoardResponse.java
│   └── Reply.java
└── user/                   # 유저 도메인
    ├── User.java
    ├── UserController.java
    ├── UserService.java
    ├── UserRepository.java
    ├── UserRequest.java
    └── UserResponse.java
```

---

## 2. Entity

```java
@NoArgsConstructor
@Data
@Entity
@Table(name = "board_tb")          // 테이블명: {도메인}_tb
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;             // PK 타입: Integer

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)  // 모든 연관관계는 LAZY
    private User user;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder                        // @Builder는 클래스가 아닌 생성자에 선언
    public Board(Integer id, String title, String content, User user, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        // 컬렉션(List 등)은 Builder 생성자에 포함하지 않는다
    }
}
```

**규칙 요약**
- 어노테이션 순서: `@NoArgsConstructor` → `@Data` → `@Entity` → `@Table`
- 테이블명: `{도메인}_tb`
- PK 타입: `Integer`
- `@Builder`는 전체 필드 생성자에만 선언 (클래스 레벨 금지)
- 컬렉션 필드는 `@Builder` 생성자에 포함하지 않는다
- 모든 연관관계: `FetchType.LAZY`
- 생성일: `@CreationTimestamp` + `LocalDateTime createdAt`

---

## 3. Repository

```java
public interface BoardRepository extends JpaRepository<Board, Integer> {
    // JpaRepository<Entity, PK타입>
}
```

---

## 4. Service

```java
@Transactional(readOnly = true)     // 클래스 레벨에 readOnly 기본 적용
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    // DTO는 Service에서 생성하여 반환한다
    // Entity를 Controller로 직접 전달하지 않는다
    @Transactional                  // 쓰기 작업에만 별도 선언
    public void save(BoardRequest.Save req) {
        ...
    }
}
```

**규칙 요약**
- 어노테이션 순서: `@Transactional(readOnly = true)` → `@RequiredArgsConstructor` → `@Service`
- 클래스 레벨에 `@Transactional(readOnly = true)` 기본 적용
- 쓰기 메서드에만 `@Transactional` 별도 선언
- **DTO는 Service에서 만든다**
- **Entity는 Controller로 전달하지 않는다**

---

## 5. Controller

```java
@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/boards")
    public String index(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "board/index";       // Mustache 템플릿 경로 반환
    }
}
```

**규칙 요약**
- 어노테이션 순서: `@RequiredArgsConstructor` → `@Controller`
- 의존성: Service, HttpSession 주입
- 반환값: Mustache 템플릿 경로(String)
- REST API의 경우 `@RestController` 사용 후 `Resp.ok()` / `Resp.fail()` 반환

---

## 6. Request DTO

```java
public class BoardRequest {          // 외부 클래스: 어노테이션 없음

    @Data
    public static class Save {       // 내부 정적 클래스: 기능명으로 명명
        private String title;
        private String content;
    }

    @Data
    public static class Update {
        private String title;
        private String content;
    }
}
```

**규칙 요약**
- 파일명: `{Domain}Request.java`
- 외부 클래스에는 어노테이션 없음
- 내부 static class 이름: 기능명 (`Save`, `Update`, `Delete` 등)
- 내부 클래스에만 `@Data` 선언

---

## 7. Response DTO

```java
public class BoardResponse {         // 외부 클래스: 어노테이션 없음

    @Data
    public static class Detail {     // 상세 조회용
        private Integer id;
        private String title;
        private String content;
        // 생성자 또는 정적 팩토리 메서드로 Entity → DTO 변환
    }

    @Data
    public static class Items {      // 목록 조회용
        private Integer id;
        private String title;
    }
}
```

**규칙 요약**
- 파일명: `{Domain}Response.java`
- 외부 클래스에는 어노테이션 없음
- 내부 static class 이름: 용도명 (`Detail`, `Items` 등)
- Entity → DTO 변환은 생성자 또는 정적 팩토리 메서드 사용

---

## 8. 공통 응답 (Resp)

```java
// 성공 응답
return Resp.ok(body);               // { status: 200, msg: "성공", body: ... }

// 실패 응답
return Resp.fail(HttpStatus.BAD_REQUEST, "에러 메시지");
```

- 위치: `_core/utils/Resp.java`
- REST API 응답은 반드시 `Resp<T>` 래퍼 사용

---

## 9. 네이밍 규칙

| 대상 | 규칙 | 예시 |
|------|------|------|
| 클래스 | PascalCase | `BoardService` |
| 메서드/변수 | camelCase | `findAll`, `createdAt` |
| 테이블명 | snake_case + `_tb` | `board_tb`, `user_tb` |
| 파일명 | PascalCase | `BoardController.java` |
| 패키지명 | lowercase | `board`, `user`, `_core` |

---

## 10. 기타 규칙

- **OSIV**: `spring.jpa.open-in-view=false`
- **Fetch 전략**: 모든 연관관계 `FetchType.LAZY` (기본 EAGER 사용 금지)
- **배치 사이즈**: `default_batch_fetch_size=10` (N+1 최적화)
- **세션 방식 인증**: `HttpSession` 사용
