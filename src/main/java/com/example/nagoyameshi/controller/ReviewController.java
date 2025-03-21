package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.Review;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.ReviewEditForm;
import com.example.nagoyameshi.form.ReviewRegisterForm;
import com.example.nagoyameshi.repository.ReviewRepository;
import com.example.nagoyameshi.repository.ShopRepository;
import com.example.nagoyameshi.security.UserDetailsImpl;
import com.example.nagoyameshi.service.ReviewService;

@Controller
@RequestMapping("/shops/{shopId}/reviews")
public class ReviewController {

	private final ReviewRepository reviewRepository;
	private final ShopRepository shopRepository;
	private final ReviewService reviewService;

	public ReviewController(ReviewRepository reviewRepository, ShopRepository shopRepository,
			ReviewService reviewService) {
		this.reviewRepository = reviewRepository;
		this.shopRepository = shopRepository;
		this.reviewService = reviewService;
	}

	@GetMapping
	public String index(@PathVariable(name = "shopId") Integer shopId,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			Model model) {
		Shop shop = shopRepository.getReferenceById(shopId);
		Page<Review> reviewPage = reviewRepository.findByShopOrderByCreatedAtDesc(shop, pageable);

		model.addAttribute("shop", shop);
		model.addAttribute("reviewPage", reviewPage);

		return "reviews/index";
	}

	@GetMapping("/register")
	public String register(@PathVariable(name = "shopId") Integer shopId, Model model) {
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		Shop shop = shopRepository.findById(shopId)
				.orElseThrow(() -> new IllegalArgumentException("指定された店舗が見つかりません"));
		model.addAttribute("shop", shop);
		model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());
		return "reviews/register";
	}

	@PostMapping("/create")
	public String create(@PathVariable(name = "shopId") Integer shopId,
			@ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm, 
			@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes,
			Model model) {
		
		Shop shop = shopRepository.getReferenceById(shopId);		
        User user = userDetailsImpl.getUser();	
        
		if (bindingResult.hasErrors()) {
			return "reviews/register";
		}

		reviewService.create(shop, user, reviewRegisterForm);
		redirectAttributes.addFlashAttribute("successMessage", "レビューを登録しました。");

		return "redirect:/shops/" + shopId + "/reviews";
	}
	
	@GetMapping("/{reviewid}/edit")
    public String edit(@PathVariable(name = "shopId") Integer shopId,
    		@PathVariable(name = "reviewId") Integer reviewId, Model model) {
        Shop shop = shopRepository.getReferenceById(shopId);
        Review review = reviewRepository.getReferenceById(reviewId);	
        
        ReviewEditForm reviewEditForm = new ReviewEditForm(review.getThought());
        
        model.addAttribute("shop", shop);
        model.addAttribute("review", review);
        model.addAttribute("reviewEditForm", reviewEditForm);
        
        return "reviews/edit";
    }    

}
