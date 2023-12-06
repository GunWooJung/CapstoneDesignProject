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
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 	//식별자
	
	@Column
	private String type;
	
	@Column 
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "placeId")
	private Place place;
	
	@Column
	private int count = 1;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	@UpdateTimestamp
	private Timestamp updateDate;
	
	 public Timestamp getCreateDate() {
          return createDate;
     }
	
	@Column
	private String ip;
	
	@Column
	private String ip2;
	
	@Column
	private String ip3;
}
