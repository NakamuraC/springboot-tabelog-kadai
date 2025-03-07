package com.example.nagoyameshi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
}
