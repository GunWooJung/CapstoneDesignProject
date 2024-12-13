package com.cos.blog.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaceMapperDTO {
	
    private long id;               // 화장실 ID
    private String name;          // 화장실 이름
    private String address;       // 화장실 주소
    private double lat;      // 위도
    private double lng;     // 경도
    private boolean disabledPerson;   // 남자장애인용 엑셀정보
	private boolean changingTableMan;	//기저귀 남자
	private boolean changingTableWoman;	//기저귀 여자
	private boolean emergencyBellMan; 	//비상벨 남자
	private boolean emergencyBellWoman; //비상벨 여자
	private boolean emergencyBellDisabled; //비상벨 장애인
    private String openTime;      // 운영 시간
	private int starCount;			//별점 개수
	private double starAverage;		//평점
	private int commentCount;		//댓글 개수
	
	// 회색은 미평가 , 1은 4.0이상 , 2는 초록색 , 3은 빨강 ,4는 노란색
	private Color color; // 마커 색상
	public enum Color{
		BLUE, //미평가
		RED, //2.0 미만
		YELLOW, //2.0~4.0
		GREEN //4.0 초과
	}
	
    //DTO로 변환
   	public PlaceMapperDTO setColor() {
   		if(this.getStarAverage() == 0) {
   			this.setColor(Color.BLUE);
   		}else if(this.getStarAverage() < 2){
   			this.setColor(Color.RED);
   		}else if(this.getStarAverage() > 4){
   			this.setColor(Color.GREEN);
   		}
   		else {
   			this.setColor(Color.YELLOW);
   		}
   		return this;
   	}

}
