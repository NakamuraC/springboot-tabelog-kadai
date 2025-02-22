package com.example.nagoyameshi.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ShopRegisterForm {
	@NotBlank(message = "店舗名を入れてください。")
	private String name;
	
	@NotNull(message = "店舗画像を選択してください。")
	private MultipartFile imageFile;
	
	@NotBlank(message = "説明を入れてください。")
	private String description;
	
	@NotNull(message = "カテゴリIDを選択してください。")
    @Min(value = 1, message = "カテゴリIDは1以上を選択してください。")
    private Integer categoryId;
	
	@NotNull(message = "予算を入力してください。")
	@Min(value = 100, message = "予算は100円以上に設定してください。")
	private Integer budget;

	@NotBlank(message = "住所を入力してください。")
    private String address;
    
    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;
}
