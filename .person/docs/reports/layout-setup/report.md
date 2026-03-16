# 작업 보고서: T-1.5 공통 화면 레이아웃 구성

## 1. 작업 개요

- **작업명**: 공통 화면 레이아웃 구성 (T-1.5)
- **일시**: 2026-03-16
- **상태**: 완료 (Completed)

## 2. 주요 구현 내용

### 2.1 공통 레이아웃 파일 생성

- **`header.mustache`**: Bootstrap 5 CSS, Font Awesome 로드 및 네비게이션 바 구현. `sessionUser` 유무에 따른 메뉴 분기 로직 포함.
- **`footer.mustache`**: Bootstrap 5 JS 로드 및 하단 저작권 정보 포함.2

### 2.2 메인 페이지 리팩토링

- **`home.mustache`**: 전체 HTML 구조를 레이아웃 기반으로 변경하여 코드 중복 제거 및 일관성 확보.

### 2.3 디자인 시스템 정의

- **`.ai/rules/design-system.md`**: 프로젝트에서 사용할 색상, 타이포그래피, 모양(Border Radius), 그림자(Box Shadow) 등의 디자인 토큰을 정의하여 향후 UI 작업의 일관성을 보장함.

## 3. 기술 스택

- **CSS Framework**: Bootstrap 5.3.3
- **Template Engine**: Mustache2
- **Icon Set**: Font Awesome 6.5.1

## 4. 향후 계획

- Phase 2의 회원가입(`join-form.mustache`) 및 로그인(`login-form.mustache`) 화면 구현 시 본 레이아웃을 상속받아 개발 진행 예정.
