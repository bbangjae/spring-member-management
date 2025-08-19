#  spring-member-management

Spring의 핵심 기능과 ORM 연동 방식을 직접 구현하고 비교하며 학습하는 CRUD 기반의 예제 프로젝트입니다.  
단순 CRUD를 넘어, Spring Data JPA + Querydsl 기반 성능 최적화, 계층형 아키텍처, 전역 예외 처리, 테스트 코드 작성까지 확장성과 유지보수성을 고려하여 구현했습니다.

## 목차
- [프로젝트 목표](#프로젝트-목표)
- [기술 및 기능](#기술-스택)
- [폴더 구조](#폴더-구조)
- [커밋 메시지 규칙](#커밋-메시지-규칙)

## 프로젝트 목표
- CRUD 기능 구현을 통해 Spring의 데이터 처리 흐름을 명확히 이해
- `Memory → MyBatis → JPA(Querydsl)` 다양한 ORM 접근 방식을 비교 및 학습
- 객체지향적 도메인 모델링과 확장성 있는 아키텍처 설계 적용
- 코드 작성 시 동작 원리를 이해하고, 재사용성과 가독성을 고려하여 작성

## 기술 스택

| 항목         | 상세 설명                                                     |
|------------|-----------------------------------------------------------|
| **프론트**    | HTML, CSS, JavaScript                                     |
| **백엔드**    | Java, Spring Boot, Spring MVC                   |
| **데이터베이스** | H2                                                        |
| **데이터 접근 계층** | Spring Data JPA, Querydsl, MyBatis, Memory Repository |
| **테스트 & 도구**  | JUnit5, P6Spy, JPA Auditing                               |

## 폴더 구조

```
spring-member-management/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── api/           # JSON API 응답용 REST Controller
│   │   │   ├── common/        # 공통 응답 클래스 (BaseResponse 등)
│   │   │   ├── controller/    # View 렌더링용 Controller
│   │   │   ├── domain/        # 도메인 
│   │   │   ├── dto/           # 요청/응답 DTO 
│   │   │   ├── exception/     # 전역 예외 처리
│   │   │   ├── mapper/        # MyBatis Mapper 인터페이스 및 XML
│   │   │   ├── repository/    # Memory, JPA Repository 인터페이스
│   │   │   └── service/       # 비즈니스 로직 처리 
│   │   ├── resources/
│   │   │   ├── static/        # 정적 자원 (CSS, JS 등)
│   │   │   └── templates/     # View 템플릿
│   └── test/                  # 테스트 코드
```

## 커밋 메시지 규칙

### 커밋 메시지 작성 형식

`````
<type>(scope): <subject>

<body>

<footer>
`````

- **타입(type):** **필수**, 커밋 타입
- **스코프(scope):** **선택**, 변경된 폴더명이나 파일명 등, 괄호로 표기
- **제목(subject):** **필수**, 한 줄로 요약(50자 이내, 마침표 X, 명령문, 첫 글자 대문자)
- **본문(body):** **선택**, 변경 이유·내용 등 상세 설명(한 행은 72자 이내, 무엇·왜에 집중)
- **푸터(footer):** **선택**, 이슈 번호, 참고 자료 등 추가 정보

### 커밋 타입

| 타입 (Type) | 설명                              | 예시                              |
|-------------|---------------------------------|-------------------------------------------------|
| feat        | 새로운 기능/코드 추가                 | feat(member): 회원 등록 기능 구현                      |
| docs        | 문서 작성/수정 (README 등)           | docs(readme): 커밋 메시지 규칙 추가                     |
| fix         | 버그 수정                          | fix(repository): 회원 조회시 NPE 예외 수정               |
| refactor    | 코드 리팩토링 (기능 변화 없음)         | refactor(service): 중복 로직 메서드로 분리                |
| test        | 테스트 코드 작성                    | test(controller): 회원 등록 API 테스트 추가                |
| chore       | 기타 작업(폴더명 변경 등)             | chore: application.yml DB 설정 수정                      |