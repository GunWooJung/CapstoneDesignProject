package com.cos.blog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Comment;
import com.cos.blog.model.Place;
import com.cos.blog.model.Report;
import com.cos.blog.model.StarRating;
import com.cos.blog.repository.CommentRepository;
import com.cos.blog.repository.OpinionRepository;
import com.cos.blog.repository.PlaceRepository;
import com.cos.blog.repository.ReportRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private PlaceRepository placeRepository;
	

	
	@Transactional
	public List<Comment> commentShow(int placeId) {
		Optional<Place> place = placeRepository.findById(placeId);
		if (place.isPresent()) {
			List<Comment> comments = commentRepository.findByPlace(place.get());
			return comments;
		} 
		else{
			return null;
		}
	}
	
	@Transactional
	public ResponseEntity<String> commentEnroll(String username,String password, String placeId, String content,String ip) {
		Optional<Place> places = placeRepository.findById(Integer.parseInt(placeId));
		if(username.equals("")||password.equals("")||content.equals("")) {
			return ResponseEntity.status(400).body("blank");
		}
		if (places.isPresent()) {
			Comment comment = new Comment();
			comment.setPlace(places.get());
			comment.setUsername(username);
			comment.setPassword(password);
			comment.setContent(content);
			comment.setIp(ip);
			List<Comment> calcomment = commentRepository.findByPlace(places.get());
			for(Comment c : calcomment) {
				if(c.getIp().equals(ip)){
					return ResponseEntity.status(400).body("fail");
				}
			}
			commentRepository.save(comment);
			Place place = places.get();
			place.setComment_count(place.getComment_count()+1);
			placeRepository.save(place);
		}
		return ResponseEntity.ok("Request successful");
	}

	@Transactional
	public void commentUpdate(int commentId) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional
	public ResponseEntity<String> commentDelete(int placeId,int commentId,String password) {
		Optional<Comment> comment = commentRepository.findById(commentId);
		if(comment.get().getPassword().equals(password)) {
			Optional<Place> p = placeRepository.findById(placeId);
			p.get().setComment_count(p.get().getComment_count()-1);
			placeRepository.save(p.get());
			commentRepository.deleteById(commentId);
			return ResponseEntity.ok("Request successful");
		}
		else {
			 return ResponseEntity.status(400).body("fail");
		}
	}
	
	@Transactional
	public void DeleteAll() {
		commentRepository.deleteAll();
	}

}
