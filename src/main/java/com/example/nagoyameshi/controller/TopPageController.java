package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.repository.CategoryRepository;

@Controller
public class TopPageController {
	private final CategoryRepository categoryRepository;
	
	public TopPageController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}
	
	@GetMapping("/")
	public String topPage(Model model) {
		
		List<Category> categories = categoryRepository.findAll();		
		model.addAttribute("categories", categories);
		
		return "topPage";
	}
}
