# 회원가입 시스템 Step 1: AJAX 중복 체크 구현 보고서

## 1. 개요
- **작업 내용**: 회원가입 시 아이디 중복 여부를 비동기(AJAX)로 확인하는 기능 구현.
- **관련 티켓**: Phase 2, T-2.1 [Step 1: AJAX 중복 체크]

## 2. 주요 변경 사항

### 2.1 Backend (Java/Spring Boot)
- **`UserService.java`**: 
  - `checkUsername(String username)` 메서드 추가: `UserRepository.findByUsername()`을 호출하여 아이디 존재 여부를 확인 (존재하지 않으면 `true` 반환).
- **`UserApiController.java`**:
  - `GET /api/check-username?username=...` 엔드포인트 추가: `UserService`를 호출하여 결과를 `Resp.ok(isAvailable)` 형태로 반환.
- **`UserController.java`**:
  - `GET /join-form` 매핑 추가: 회원가입 화면(`user/join-form.mustache`)으로 이동.

### 2.2 Frontend (Mustache/JavaScript)
- **`src/main/resources/templates/user/join-form.mustache`**:
  - 회원가입 폼 구성 (아이디, 비밀번호, 이메일).
  - **Fetch API**를 활용한 `checkUsername()` 함수 구현:
    - 서버의 `/api/check-username`과 통신하여 실시간 중복 체크.
    - 체크 결과에 따라 메시지(사용 가능/중복됨) 노출 및 색상 변경.
    - 입력값 변경 시 중복 체크 상태 초기화 로직 포함.
  - **폼 제출 제어**: 중복 체크가 완료되지 않은 경우 가입 시도 차단 (`alert` 노출).

## 3. 검증 결과
- `./gradlew build` 성공.
- 더미 데이터(`ssar`, `cos`)를 활용하여 실제 구동 시 중복 체크 로직이 정상 작동함을 확인 가능하도록 설계됨.

## 4. 향후 계획 (Step 2)
- `UserRequest.JoinDTO` 구현 및 AOP를 활용한 공통 유효성 검사(Validation) 적용.
- BCrypt 라이브러리 추가 및 해시 암호화 적용.
