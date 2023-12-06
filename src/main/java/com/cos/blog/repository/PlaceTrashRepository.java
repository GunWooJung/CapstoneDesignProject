package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cos.blog.model.Place;

public interface PlaceTrashRepository extends JpaRepository<Place, Integer> {


}
