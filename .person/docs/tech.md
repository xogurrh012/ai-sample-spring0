# 기술 명세서 (Technical Specification)

## 1. 기술 스택 (Tech Stack)

### 1.1 Backend
- **Language**: Java 21
    - 적극 활용: `var`, `Record`, `Switch Expressions`, `Text Blocks`
- **Framework**: Spring Boot 3.3.4
- **Build Tool**: Gradle
- **Persistence**: 
    - Spring Data JPA
    - Hibernate
    - QueryDSL (필요 시 도입 고려)
- **Database**: H2 (In-memory/File 기반 개발용)
- **Security**: 
    - JWT (JSON Web Token) 기반 자체 인증/인가
    - 서블릿 필터(Filter)를 이용한 토큰 검증

### 1.2 View & API
- **View Engine**: Mustache (HTML 렌더링용)
- **API Style**: REST API (JSON 응답 위주)

## 2. 시스템 아키텍처 (System Architecture)

### 2.1 레이어드 아키텍처 (Layered Architecture)
- **Controller**: 요청 매핑, 입력 유효성 검사, 응답 구성
- **Service**: 핵심 비즈니스 로직, 트랜잭션 관리, DTO 변환
- **Repository**: 데이터베이스 접근 (Entity 중심)
- **Entity**: 도메인 모델 정의 (JPA)

### 2.2 패키지 구조 (Package Structure)
- `com.example.demo.[domain]`: 도메인 단위로 패키지 구성 (board, user, reply 등)
- `com.example.demo._core`: 공통 유틸리티, 예외 처리, 보안 설정 등

## 3. 주요 기술적 의사결정 (Technical Decisions)

### 3.1 데이터 관리 및 트랜잭션
- **Transactional**: 
    - 서비스 레벨에서 `@Transactional(readOnly = true)`를 기본으로 설정하여 성능 최적화.
    - CUD(Create, Update, Delete) 작업은 명시적으로 `@Transactional` 부여.
- **DTO 반환**:
    - 엔티티(Entity)는 서비스 레이어를 벗어나지 않으며, 컨트롤러로는 반드시 DTO(Record 활용 권장)를 반환함.

### 3.2 인증 및 인가 (Security)
- **JWT 사용**: Stateless한 서버 구성을 위해 세션 대신 JWT를 사용함.
- **Access Token**: 헤더(`Authorization: Bearer <token>`)를 통해 전달.
- **예외 처리**: `RestAdvice` 등을 통해 공통 에러 응답 형식을 정의하고 처리함.

### 3.3 데이터 모델링 및 제약 조건
- **Soft Delete**: 회원 탈퇴 시 게시글과 댓글을 즉시 물리적으로 삭제하지 않고 상태값을 통해 비노출 처리(Soft Delete) 하는 방식을 고려함. (PRD의 '비노출' 요건 준수)
- **Fetch Strategy**: Lazy Loading을 기본으로 하며, N+1 문제 발생 시 `Fetch Join` 또는 `Batch Size` 설정을 통해 해결함.

## 4. 인프라 및 환경 설정
- **H2 DB**: 개발 편의성을 위해 파일 기반 H2 DB를 사용하며, 서버 재시작 시에도 데이터가 유지될 수 있도록 설정함.
- **Application Config**: `application.properties` 또는 `YAML`을 통해 환경별 설정 관리.

## 5. 테스트 전략
- **Unit Test**: JUnit 5와 AssertJ를 이용한 핵심 로직 테스트.
- **Integration Test**: `@SpringBootTest`를 활용한 전체 레이어 통합 테스트 및 API 동작 검증.
- **Mocking**: `Mockito`를 활용하여 외부 의존성(Repository 등)을 목킹함.
