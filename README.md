

# 소개 영상 보기 : UCC 링크

# 프로젝트 진행 기간 
 2023.01.03(화) ~  2023.02.17(금) (45일간 진행) <br>
 SSAFY 8기 2학기 공통 프로젝트 - OSIOPSO


 # OSIOPSO - 배경

 # OSIOPSO - 개요

 # 주요 기능

 # 주요 기술

 # 프로젝트 파일 구조
 ### Back
```
Osiopso
├── config
├── controller
├── dto
│   ├── request
│   │   ├── closet
│   │   ├── comment
│   │   ├── feed
│   │   └── filter
│   ├── response
│   │   ├── closet
│   │   ├── comment
│   │   ├── feed
│   │   └── tag
│   ├── tag
│   └── user
├── entity
│   ├── closet
│   ├── comment
│   ├── feed
│   ├── tag
│   └── user
├── exception
├── repository
│   ├── article
│   ├── closet
│   ├── comment
│   └── user
├── security
│   └── oauth2
│       └── user
├── service
│   ├── article
│   ├── closet
│   └── user
└── util
```

 ### Front
 ```
 ```


 # 형상관리 및 협업 툴
 - 1
 - 2
 - 3
 - 4


 # 협업 환경
 - 1 
 - 2
 - 3


 # 팀원 역활 분배
img


# 프로젝트 산출물
- 기능명세서
- 디자인&컨셉기획
- 스토리보드
- 시퀀스다이어그램
- 아키텍처
- 와이어프레임
- 컨벤션
- API
- ERD
- 회의록
- 테스트케이스
- 시스템기술서
- 명세기술서



# 프로젝트 결과물
- 포팅메뉴얼
- 중간발표자료
- 최종발표자료



# Osiopso 서비스 (구동화면)
- 1
- 2
- 3



---
---
---


# BTS 봉준호 제이팍 코진스 Let's go

## 🖤 브랜치 생성 컨벤션

1. 최대한 Git Flow를 따라주세요.
2. 상세 기능은 소제목 형식으로 하겠습니다.
- GitFlow브랜치명/지라이슈넘버-지라이슈이름
- 공백에는 하이픈(-)을 넣어주시면 됩니다.
- ex) Feature/S08P12C106-91-BE-옷장-등록

## 🖤 커밋 메시지 컨벤션

- 접두사와 콜론은 붙이고, 콜론 후 한 칸은 띄어쓰기
- git commit -m "접두사: 메시지"

## 🖤 Daily Docs 컨벤션

- git commit -m "Docs: 월-일 이름 Daily"
- ex) git commit -m "Docs: 01-30 희주 Daily"

### 1. 커밋 유형 지정

- 커밋 유형은 영어 대문자로 작성하기
  
  | 커밋 유형            | 의미                                       |
  | ---------------- | ---------------------------------------- |
  | Feat             | 새로운 기능 추가                                |
  | Fix              | 버그 수정                                    |
  | Docs             | 문서 수정                                    |
  | Style            | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
  | Refactor         | 코드 리팩토링                                  |
  | Test             | 테스트 코드, 리팩토링 테스트 코드 추가                   |
  | Chore            | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore     |
  | Design           | CSS 등 사용자 UI 디자인 변경                      |
  | Comment          | 필요한 주석 추가 및 변경                           |
  | Rename           | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우            |
  | Remove           | 파일을 삭제하는 작업만 수행한 경우                      |
  | !BREAKING CHANGE | 커다란 API 변경의 경우                           |
  | !HOTFIX          | 급하게 치명적인 버그를 고쳐야 하는 경우                   |

### 2. 제목과 본문을 빈행으로 분리

- 커밋 유형 이후 제목과 본문은 한글로 작성하여 내용이 잘 전달될 수 있도록 할 것
- 본문에는 변경한 내용과 이유 설명 (어떻게보다는 무엇 & 왜를 설명)

### 3. 제목 첫 글자는 대문자로, 끝에는 `.` 금지

### 4. 제목은 영문 기준 50자 이내로 할 것

### 5. 자신의 코드가 직관적으로 바로 파악할 수 있다고 생각하지 말자

### 6. 여러가지 항목이 있다면 글머리 기호를 통해 가독성 높이기

### 7. 🖤 한 커밋에는 한 가지 문제만!

- 추적 가능하게 유지해주기
- 너무 많은 문제를 한 커밋에 담으면 추적하기 어렵다.

```
- 변경 내용 1
- 변경 내용 2
- 변경 내용 3
```

### 🖤 규칙에 맞는 좋은 커밋메시지를 작성해야 하는 이유

- 팀원과의 소통
- 편리하게 과거 추적 가능
- 나중에 실무에서 익숙해지기 위해

