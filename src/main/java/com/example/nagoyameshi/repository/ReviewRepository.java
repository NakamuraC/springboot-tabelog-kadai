package com.example.nagoyameshi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer>{
	public Page<Review> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
	
	public Page<Review> findByShopOrderByCreatedAtDesc(Shop shop, Pageable pageable);
}
