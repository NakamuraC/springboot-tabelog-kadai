package com.example.nagoyameshi.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.repository.CategoryRepository;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {
	private final CategoryRepository categoryRepository;

	public AdminCategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;                
    }

	@GetMapping
	public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		List<Category> categories;

		if (keyword != null && !keyword.trim().isEmpty()) {
		    categories = categoryRepository.findByNameContaining(keyword);
		} else {
		    categories = categoryRepository.findAll();
		}
		
		model.addAttribute("categories", categories);
		model.addAttribute("keyword", keyword);

		return "admin/categories/adminCategory";
	}
}
