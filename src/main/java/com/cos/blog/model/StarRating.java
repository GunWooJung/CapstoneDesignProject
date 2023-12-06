package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class StarRating {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 	//식별자
	
	@Column
	private String ip;
	
	@Column
	private double score;
	
	@ManyToOne
	@JoinColumn(name = "placeId")
	private Place place;
	
	@CreationTimestamp
	private Timestamp createDate;

		
}
