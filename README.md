# 🏠 모두의 화장실

📍 **공공데이터를 활용한 화장실 지도 서비스**  

## 🎯 백엔드 모든 기능 + CI/CD 및 클라우드 배포

"모두의 화장실"은 공공데이터를 기반으로 한 **화장실 위치 제공 서비스**입니다.  
사용자의 현재 위치를 기반으로 **가까운 개방 화장실을 필터링 및 검색**하고,  
**시설 정보, 리뷰, 정보 수정 제안 기능**을 제공합니다. 🚻  

---

## 🛠️ 기술 스택

🚀 **Frontend**: HTML, CSS, JavaScript  
🛠 **Backend**: Spring Boot, JPA, MyBatis, MySQL, Jenkins, Naver Cloud Platform  
📡 **API**: Kakao Map API  

---

## ✨ 주요 기능

✅ **화장실 검색** – 사용자의 위치를 기준으로 화장실 명의 키워드 검색 기능을 제공합니다.  
✅ **화장실 필터링** – 평점, 장애인 화장실 여부, 기저귀 교환대 여부 정보를 바탕으로 필터링 기능을 제공합니다.  
✅ **별점** – 사용자가 화장실에 대한 별점을 남길 수 있습니다. ⭐⭐⭐⭐⭐  
✅ **댓글** – 사용자가 화장실에 대해 댓글을 작성할 수 있습니다. 💬  
✅ **정보 수정 제안** – 특정 화장실에 대한 정보 수정을 제안하고, 타인과 공유하며,  
   공감 기능을 통해 공감 횟수를 추가할 수 있습니다. 👍  

---

## ⚡ DB 성능 개선 관련 내용

📌 **블로그** : [DB 성능 개선 블로그 링크](https://gw7193.tistory.com/24)  

### 🔍 주요 개선 사항  

#### 1️⃣ **반정규화를 통한 화장실 목록 API 조회 성능 향상**

- **반정규화**를 통해 화장실 테이블에 평점, 별점 개수, 댓글 수와 같은 **필터링 시 필요한 통계 칼럼**을 추가하였습니다.  
  이로 인해 화장실 목록 API의 **요청 시간**이 **평균 601ms → 14ms**로 크게 향상되었습니다.
  
  - 개선 전: 각 화장실에 대한 별점, 댓글 수를 테이블을 모두 조인하여 조회하여 성능 저하 발생
  - 개선 후: 필터링에 필요한 정보를 미리 저장하여 쿼리 최적화

#### 2️⃣ **합성 키 인덱스(Index) 적용**

- **위도(lat)와 경도(lng)** 컬럼에 대해 **합성 키 인덱스**를 적용하여 조회 성능을 향상시켰습니다.  
  이를 통해 **API 요청 시간**이 **평균 11ms → 4ms**로 단축되었습니다.
  
  또한, **합성 키 인덱스(lat, lng)와 단일 키 인덱스(lat) 및 단일 키 인덱스(lng)를** 동시에 적용한 것과 성능을 비교 분석 하였습니다.

#### 3️⃣ **JPA N+1 문제 해결**

- 기존에 댓글 목록 조회 시 **작성자 명**을 가져오는 과정에서 **N+1 문제**가 발생하였습니다.  
  이를 해결하기 위해 **Fetch Join** 방식을 도입하여 API 요청 시간이 **평균 10ms → 6ms**로 개선되었습니다.
  
  - 개선 전: 댓글 목록을 조회할 때마다 작성자 정보를 별도로 조회하여 성능 저하가 발생  
  - 개선 후: **Fetch Join**을 활용하여 댓글 목록과 작성자 정보를 한 번의 쿼리로 조회하여 성능을 최적화
    
  또한, **Fetch Join**이 중복 데이터를 발생시키거나 비효율적일 수 있는 상황이 있으므로, 적절한 상황에서만 이 방식을 적용해야 함을 분석하였습니다.


#### 4️⃣ **트랜잭션 격리 수준(Isolation Level) 및 락(Locking) 적용**

- 발생 가능한 트랜잭션 이상 현상을 분석하여, **READ COMMITTED** 격리 수준을 설정하고, **SELECT FOR UPDATE**(비관적 락)을 적용하여 데이터 무결성을 유지하였습니다.  
- 또한, **비관적 락**을 적용한 방식과 **낙관적 락**을 적용한 방식의 차이를 분석하여, 각각의 성능 차이를 비교 분석하였습니다.
  
---


## 🏗️ 시스템 아키텍처

![시스템 아키텍처](https://github.com/GunWooJung/READMEImage/blob/main/%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98.JPG)

---

## 📺 시연 영상 및 문서

📌 **시연 영상**: [YouTube 링크](https://www.youtube.com/watch?v=HhzYp6_m4iM)  
📌 **E-R 다이어그램**: [ERD Cloud 링크](https://www.erdcloud.com/d/YYW2iJdB7WtefCCPM)  
📌 **API 문서**: [Notion 링크](https://superb-piper-d4a.notion.site/156ad387decd81098a5dea41c407b662?v=156ad387decd8108a31f000c474b4446)  
📌 **공공 데이터 출처**: [서울 열린 데이터 광장](https://data.seoul.go.kr/dataList/OA-162/S/1/datasetView.do)  
