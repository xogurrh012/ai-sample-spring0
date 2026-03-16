# Design System

## 디자인 토큰 (Design Tokens)

### Colors
- **Primary**: `#197fe6` (브랜드 주요 색상)
- **Background**: `#f8f9fa` (전체 배경색)
- **Navbar Background**: `#ffffff` (네비게이션 바 배경색)

### Typography
- **Font Family**: `Plus Jakarta Sans`, `Pretendard`, sans-serif
- **Base Size**: Bootstrap 5 기본 설정 준수

### Shapes & Shadows
- **Border Radius**: `12px` (카드, 버튼 등 주요 요소)
- **Box Shadow**: `0 4px 20px rgba(0, 0, 0, 0.05)` (네비게이션 바, 카드 등)

## 공통 컴포넌트 규칙
- **Header**: Bootstrap 5 Navbar 활용, `sessionUser` 존재 여부에 따른 메뉴 분기 처리.
- **Footer**: `mt-auto`를 활용하여 페이지 하단 고정, 저작권 정보 표시.
- **Container**: 모든 본문 내용은 Bootstrap `.container` 클래스 내부에 배치하여 중앙 정렬 및 여백 확보.

## 기술 스택
- **Framework**: Bootstrap 5.3.3
- **Icons**: Font Awesome 6.5.1
- **Template Engine**: Mustache
