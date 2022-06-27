## TravelDiary : 나만의 여행일지를 만들어가는 웹 사이트

내가 다녀온 여행지를 기록하고 내가 원하는 사람들과 공유하여 서로의 추억을 보관할 수 있는 사이트가 있으면 좋을 것 같다.

http://ec2-15-165-101-214.ap-northeast-2.compute.amazonaws.com/

<br>

<br>

## 주요기능

- **회원가입과 로그인** : 세션을 이용해서 개발
- **게시판 검색** : 날짜를 입력하면 해당 날짜에 입력한 게시글들을 출력
- **게시글 저장, 수정, 삭제** : 내가 다녀온 여행지를 기록하고 관리하는 기능
- **프로필 수정** : 내 프로필 정보(비밀번호, 이메일, 사진)를 수정하는 기능
- **공유폴더 생성** : 내가 작성한 게시글 중에서 원하는 게시글을 원하는 사람에게 공유할 수 있는 기능

<br>

<br>

## 기술스택

- Frontend
  - Thymeleaf
  - CSS, BootStrap
  - JavaScript
- BackEnd
  - Java - 11
  - SpringMVC, SpringBoot, Spring Data JPA
  - QueryDSL
  - Gradle
  - Junit5, Mockito
- DevOps
  - MySQL
  - EC2, S3, CodeDeploy
  - Travis CI
  - Nginx


<br>

<br>

## API 명세서

https://believed-park-6fb.notion.site/API-3321f2d3184841f6afd1bac7842f50d4

<br>

<br>

## ERD
![01  ERD](https://user-images.githubusercontent.com/63215089/176001251-6a384cd2-bb78-411d-86ba-faa13c0538b4.png)


<br>

<br>

## 리팩토링

JPQL에서 QueryDSL로 [전환하기](https://solidbasics.tistory.com/44?category=1064030)

통합테스트로 작성된 테스트코드를 [단위테스트로 변경](https://solidbasics.tistory.com/34?category=1077150)

<br>

<br>

## 문제해결

https://solidbasics.tistory.com/category/%EC%97%90%EB%9F%AC%EB%AA%A8%EC%9D%8C

<br>

<br>

## 프로젝트 정리

