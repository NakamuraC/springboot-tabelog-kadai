package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.repository.UserRepository;

@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final ShopRepository shopRepository;  
    private final UserRepository userRepository;


	public ReviewService(ReviewRepository reviewRepository, ShopRepository shopRepository, UserRepository userRepository) {
	       this.reviewRepository = reviewRepository;        
	       this.shopRepository = shopRepository;
	       this.userRepository = userRepository;
	   }

	@Transactional
	public void create(Shop shop, User user,  ReviewRegisterForm reviewRegisterForm) {
		Review review = new Review();
		
		review.setShop(shop);
		review.setUser(user);
		review.setThought(reviewRegisterForm.getThought());

		reviewRepository.save(review);
	}
	
	@Transactional
	public void update(Shop shop, User user,  ReviewEditForm reviewEditForm) {
		Review review = reviewRepository.getReferenceById(reviewEditForm.getId());
		
		review.setShop(shop);
		review.setUser(user);
		review.setThought(reviewEditForm.getThought());

		reviewRepository.save(review);
	}

}
