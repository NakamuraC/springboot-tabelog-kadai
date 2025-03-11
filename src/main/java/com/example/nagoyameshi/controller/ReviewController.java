package com.example.nagoyameshi.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.service.ReviewService;


@Controller		
@RequestMapping("/shops/{shopId}/reviews")		
public class ReviewController {		
    private final ReviewRepository reviewRepository;		
    private final ShopRepository shopRepository; 		
    private final ReviewService reviewService; 		
    		
    public ReviewController(ReviewRepository reviewRepository, ShopRepository shopRepository, ReviewService reviewService) {        		
        this.reviewRepository = reviewRepository;		
        this.shopRepository = shopRepository;		
        this.reviewService = reviewService;		
    }     		
    		
    @GetMapping		
    public String index(@PathVariable(name = "shopId") Integer shopId, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {		
        Shop shop = shopRepository.getReferenceById(shopId);
        Page<Review> reviewPage = reviewRepository.findByShopOrderByCreatedAtDesc(shop, pageable);       		
                    		
        model.addAttribute("shop", shop); 		
        model.addAttribute("reviewPage", reviewPage);
        
        		
        return "reviews/index";		
    }    
		
}


	