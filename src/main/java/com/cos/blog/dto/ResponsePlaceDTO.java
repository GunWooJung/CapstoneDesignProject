package com.cos.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Builder
public class ResponsePlaceDTO {
	
    private final long id;               // 화장실 ID
    @NonNull
    private final String name;          // 화장실 이름
    @NonNull
    private final String address;       // 화장실 주소
    private final double lat;      // 위도
    private final double lng;     // 경도
    private final boolean disabledPerson;   // 남자장애인용 엑셀정보
	private final boolean changingTableMan;	//기저귀 남자
	private final boolean changingTableWoman;	//기저귀 여자
	private final boolean emergencyBellMan; 	//비상벨 남자
	private final boolean emergencyBellWoman; //비상벨 여자
	private final boolean emergencyBellDisabled; //비상벨 장애인
	@NonNull
    private final String openTime;      // 운영 시간
	private final int starCount;			//별점 개수
	private final double starAverage;		//평점
	private final int commentCount;		//댓글 개수
	
	// 회색은 미평가 , 1은 4.0이상 , 2는 초록색 , 3은 빨강 ,4는 노란색
	@Setter
	private Color color; // 마커 색상
	public enum Color{
		BLUE, //미평가
		RED, //2.0 미만
		YELLOW, //2.0~4.0
		GREEN //4.0 초과
	}
	
    // 두 점 사이 거리
    public double distance(double curLat, double curLng, double lat, double lng) {
    	return (curLat - lat) * (curLat - lat) +
		 0.090100236513120846942223223335961
		* 0.090100236513120846942223223335961 
		* (curLng - lng ) * (curLng - lng);
    }
}
