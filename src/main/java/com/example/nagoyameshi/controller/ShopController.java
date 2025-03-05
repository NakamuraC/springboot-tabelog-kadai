package com.example.nagoyameshi.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.repository.CategoryRepository;
import com.example.nagoyameshi.repository.ShopRepository;

@Controller
@RequestMapping("/shops")
public class ShopController {
	private final ShopRepository shopRepository;
	private final CategoryRepository categoryRepository;

	public ShopController(ShopRepository shopRepository, CategoryRepository categoryRepository) {
        this.shopRepository = shopRepository;     
        this.categoryRepository = categoryRepository;
    }

	@GetMapping
    public String index(@RequestParam(name = "keyword", required = false)String keyword,
    		@RequestParam(name = "area", required = false)String area,
    		@RequestParam(name = "budget", required = false)Integer budget,
    		@RequestParam(name = "category", required = false)Category category,
    		@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC)
    Pageable pageable, Model model) {
    
		Page<Shop> shopPage;
		
		if(keyword != null && !keyword.isEmpty()) {
			shopPage = shopRepository.findByNameLikeOrAddressLike("%" +  keyword + "%", "%" +  keyword + "%", pageable);
		}else if(area != null && !area.isEmpty()) {
			shopPage = shopRepository.findByAddressLike("%" +  area + "%", pageable);
		}else if(budget != null) {
			shopPage = shopRepository.findByBudgetLessThanEqual(budget, pageable);
		}else if(category != null) {
			shopPage = shopRepository.findByCategoryNameLike("%" +  category + "%", pageable);
		}else {
			shopPage = shopRepository.findAll(pageable);
		}
		
		model.addAttribute("shopPage", shopPage);
		model.addAttribute("keyword", keyword);
		model.addAttribute("area", area);
		model.addAttribute("budget", budget);
		model.addAttribute("category", category);
		
		return "shops/ListShop";
		
			}
    }
