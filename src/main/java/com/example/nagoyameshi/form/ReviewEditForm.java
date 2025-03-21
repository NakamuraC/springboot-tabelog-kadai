package com.example.nagoyameshi.form;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewEditForm {
	
	@NotBlank(message = "レビューを入力してください。")
    private String thought;   
}
