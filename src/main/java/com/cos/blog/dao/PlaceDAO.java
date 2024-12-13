package com.cos.blog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.cos.blog.dto.response.PlaceMapperDTO;

@Mapper
public interface PlaceDAO {

	//마이바티스 정규화 버전
    @Select("""
        SELECT 
                p.id AS id,
			    p.name AS name,
			    p.lat AS lat,
			    p.lng AS lng,
			    p.address AS address,
			    p.disabled_person AS disabledPerson, 
			    p.changing_table_man AS changingTableMan,
			    p.changing_table_woman AS changingTableWoman,
			    p.emergency_bell_man AS emergencyBellMan,
			    p.emergency_bell_woman AS emergencyBellWoman,
			    p.emergency_bell_disabled AS emergencyBellDisabled,
			    p.open_time AS openTime,
	            COUNT(c.id) AS commentCount, 
	            COUNT(s.id) AS starCount, 
	            COALESCE(AVG(s.score), 0) AS starAverage
        FROM place p
        LEFT JOIN comment c ON c.place_id = p.id
        LEFT JOIN star_rating s ON s.place_id = p.id
        WHERE p.lat BETWEEN #{latFrom} AND #{latTo}
    	AND p.lng BETWEEN #{lngFrom} AND #{lngTo}
        GROUP BY p.id, p.name, p.lat, p.lng, p.address, 
                 p.disabled_person, p.changing_table_man, p.changing_table_woman,
                 p.emergency_bell_man, p.emergency_bell_woman, p.emergency_bell_disabled, 
                 p.open_time
        """)
    List<PlaceMapperDTO> getPlaces(
        @Param("latFrom") double latFrom,
        @Param("latTo") double latTo,
        @Param("lngFrom") double lngFrom,
        @Param("lngTo") double lngTo
    );
    
    //마이바티스 반정규화 버전
    @Select("""
            SELECT 
                    p.id AS id,
    			    p.name AS name,
    			    p.lat AS lat,
    			    p.lng AS lng,
    			    p.address AS address,
    			    p.disabled_person AS disabledPerson, 
    			    p.changing_table_man AS changingTableMan,
    			    p.changing_table_woman AS changingTableWoman,
    			    p.emergency_bell_man AS emergencyBellMan,
    			    p.emergency_bell_woman AS emergencyBellWoman,
    			    p.emergency_bell_disabled AS emergencyBellDisabled,
    			    p.open_time AS openTime,
    	            p.comment_count AS commentCount, 
    	            p.star_count AS starCount, 
    	            p.star_average AS starAverage 
            FROM place p
            WHERE p.lat BETWEEN #{latFrom} AND #{latTo}
    		AND p.lng BETWEEN #{lngFrom} AND #{lngTo}
            """)
        List<PlaceMapperDTO> getPlaces2(
            @Param("latFrom") double latFrom,
            @Param("latTo") double latTo,
            @Param("lngFrom") double lngFrom,
            @Param("lngTo") double lngTo
        );
}
