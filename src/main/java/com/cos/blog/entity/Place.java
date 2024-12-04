package com.cos.blog.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.cos.blog.dto.ResponsePlaceDTO;
import com.cos.blog.dto.ResponsePlaceDTO.Color;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "place")
public class Place {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; 	//PK
	
	@NonNull
	@Column(name = "name", updatable = false, nullable = false, length = 300)
	private String name; //화장실 이름
	
	@NonNull
	@Column(name = "address" , updatable = false)
	private String address;	//화장실 주소
	
	@Column(name = "lat" , updatable = false)
	private double lat; // 위도
	
	@Column(name = "lng" , updatable = false)
	private double lng; // 경도

	@Column(name = "disabled_person", nullable = false)
	private boolean disabledPerson; //장애인용 엑셀정보
	
	@Column(name = "changing_table_man", nullable = false)
	private boolean changingTableMan; //기저귀교환대 유무 엑셀정보
	
	@Column(name = "changing_table_woman", nullable = false)
	private boolean changingTableWoman; //기저귀교환대 유무 엑셀정보
	
	@Column(name = "emergency_bell_man", nullable = false)
	private boolean emergencyBellMan; // 비상벨 유무
	
	@Column(name = "emergency_bell_woman", nullable = false)
	private boolean emergencyBellWoman; // 비상벨 유무
	
	@Column(name = "emergency_bell_disabled", nullable = false)
	private boolean emergencyBellDisabled; // 비상벨 유무
	
	@NonNull
	@Column(name = "open_time", nullable = false)
	private String openTime; // 운영 시간
	
	//반정규화 별점 개수
	@Setter
	@Column(name = "star_count")
	private int starCount;
	
	//반정규화 평점
	@Setter
	@Column(name = "star_average")
	private double starAverage;
	
	//반정규화 댓글 개수
	@Setter
	@Column(name = "comment_count")
	private int commentCount;
	
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private Timestamp createdDate; // 생성 시간

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private Timestamp updatedDate; // 수정 시간

    //DTO로 변환
	public ResponsePlaceDTO toPlaceResponseDTO() {
		ResponsePlaceDTO dto = ResponsePlaceDTO.builder()
        		.id(id)
        		.name(name)
        		.address(address)
        		.lat(lat)
        		.lng(lng)
        		.disabledPerson(disabledPerson)
        		.changingTableMan(changingTableMan)
        		.changingTableWoman(changingTableWoman)
        		.emergencyBellMan(emergencyBellMan)
        		.emergencyBellWoman(emergencyBellWoman)
        		.emergencyBellDisabled(emergencyBellDisabled)
        		.openTime(openTime)
        		.starCount(starCount)
        		.starAverage(starAverage)
        		.commentCount(commentCount)
        		.build();
		if(dto.getStarAverage() == 0) {
			dto.setColor(Color.BLUE);
		}else if(dto.getStarAverage() < 2){
			dto.setColor(Color.RED);
		}else if(dto.getStarAverage() > 4){
			dto.setColor(Color.GREEN);
		}
		else {
			dto.setColor(Color.YELLOW);
		}
		return dto;
	}
}
