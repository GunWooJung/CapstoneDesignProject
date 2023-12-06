package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Opinion;
import com.cos.blog.model.Place;
import com.cos.blog.repository.OpinionRepository;
import com.cos.blog.repository.PlaceRepository;

@Service
public class OpinionService {
	
	@Autowired
	private OpinionRepository opinionRepository;
	
	@Transactional
	public ResponseEntity<String> saveOpinion(int placeid, String content) {
		Opinion o = new Opinion();
		o.setContent(content);
		o.setPlaceId(placeid);
		opinionRepository.save(o);
		return  ResponseEntity.status(400).body("user_opinion");
	}
	
	@Transactional
	public List<Opinion> opinionShow() {
		List<Opinion> o = opinionRepository.findAll();
		return o;
	}
}
