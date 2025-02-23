package com.example.nagoyameshi.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.nagoyameshi.entity.Category;
import com.example.nagoyameshi.entity.Shop;
import com.example.nagoyameshi.form.ShopRegisterForm;
import com.example.nagoyameshi.repository.ShopRepository;

@Service
public class ShopService {
	private final ShopRepository shopRepository;

	public ShopService(ShopRepository shopRepository) {
		this.shopRepository = shopRepository;
	}

	@Transactional
	public void create(ShopRegisterForm shopRegisterForm) {
		Shop shop = new Shop();
		MultipartFile imageFile = shopRegisterForm.getImageFile();

		String imageName = imageFile.getOriginalFilename();
		String hashedImageName = generateNewFileName(imageName);
		Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
		copyImageFile(imageFile, filePath);
		shop.setImageName(hashedImageName);

		shop.setName(shopRegisterForm.getName());
		shop.setDescription(shopRegisterForm.getDescription());

		Integer categoryId = shopRegisterForm.getCategoryId();
		Category category = shopRepository.findById(categoryId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid category ID: " + categoryId));
		shop.setCategory(category);

		shop.setBudget(shopRegisterForm.getBudget());
		shop.setAddress(shopRegisterForm.getAddress());
		shop.setPhoneNumber(shopRegisterForm.getPhoneNumber());

		shopRepository.save(shop);
	}

	public String generateNewFileName(String fileName) {
		String[] fileNames = fileName.split("\\.");
		for (int i = 0; i < fileNames.length - 1; i++) {
			fileNames[i] = UUID.randomUUID().toString();
		}

		String hashedFileName = String.join(".", fileNames);
		return hashedFileName;
	}

	public void copyImageFile(MultipartFile imageFile, Path filePath) {
		try {
			Files.copy(imageFile.getInputStream(), filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
