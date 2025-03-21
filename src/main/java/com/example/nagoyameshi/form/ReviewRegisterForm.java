package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ReviewRegisterForm {
	@NotBlank(message = "レビューを入力してください。")
    private String thought;   
}
