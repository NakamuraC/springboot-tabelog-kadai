package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;


@Controller
@RequestMapping("/reviews")
public class ReviewController {
	private final ReviewRepository reviewRepository;
	private final ShopRepository shopRepository;
	
	public ReviewController(ReviewRepository reviewRepository, ShopRepository shopRepository) {
		this.reviewRepository = reviewRepository;
		this.shopRepository = shopRepository;
	}

	@GetMapping
	public String index(Model model) {
    	List<Review> reviews = reviewRepository.findAll();
    	
		model.addAttribute("reviews", reviews);

		return "reviews/reviewList";
	}
		
}


	