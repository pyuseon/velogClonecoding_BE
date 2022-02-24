# velogClonecoding_BE

# 😺 velog 클론코딩
* 노션 : https://www.notion.so/7-Velog-d3201fffe64e487fa39ee6aebcbc8008 
* 프론트엔드 Github : https://github.com/cyjin463/cloneW7.git


##  프로젝트 소개 
개발자 블로그! 벨로그를 클론 코딩 합니다. 
  
<img src="https://user-images.githubusercontent.com/44867889/155516575-2af9393b-63a3-4c98-8621-90c165d7aae1.png" width="70%" height="70%">   

## :movie_camera: 시연 영상 
https://www.youtube.com/watch?v=meAVU7i0XpE

## :scroll:  ERD 설계 

<img src="https://user-images.githubusercontent.com/44867889/155517060-82c44db6-ac9c-4a38-a716-be50efa71c31.png" width="80%" height="80%">


## :smile: API 테이블  

<details> 
    <summary>여기를 눌러주세요</summary>   

![image](https://user-images.githubusercontent.com/44867889/155517228-99305bbf-877d-4c9a-8d94-e01973da535e.png)
![image](https://user-images.githubusercontent.com/44867889/155517317-4c73f72f-3bdc-4ef4-ba4b-393aadc99c12.png)
![image](https://user-images.githubusercontent.com/44867889/155517402-194837ae-d0f5-4bbb-bcd7-a267c06ffd46.png)

   
</details>  

## :sweat_drops: 개발 기간 및 개인 역할   
**기간 : 2022.02.18 ~ 2022.02.24 (6일)**   
* 김정근 : 댓글 수정, 삭제, 생성 / 태그 생성, 조회
* 송성근 : 댓글 수정, 삭제, 생성, 조회 
* 박유선 : 로그인, 회원가입 / 이미지 업로드
  
  
## :notes: 기술 스택   
Back-end

* Java 8
* SpringBoot 2.5.3
* Spring Security
* Gradle
* JPA
* MySQL
* JWT
* CORS


DevOps

* AWS EC2
* AWS RDS(MySQL)
* FileZilla

Tool

* Git
* GitHub  


## :dancer: 우리팀이 해결한 문제 
1. User정보를 로그인시 가져오지 못해 해당 컬럼에  fetch = FetchType.EAGER 를 설정하였다. 
2. like를 취소하는 동작이 잘 안되는 문제가 있어서 orphanRemoval = true 를 User에 추가해 주었다. 
3. 포스팅을 받아올 때 이미지 url 을 리스트로 받아와야 하는데 손쉽게 받아오기 위해 이미지 테이블을 생성하여 포스팅 테이블과 연관관계를 설정해 주었다. 
    
