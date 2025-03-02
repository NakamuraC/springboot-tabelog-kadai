package com.example.nagoyameshi.controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.form.PasswordResetForm;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.service.UserService;

public class PasswordResetController {
private final UserService userService;    
    
    public PasswordResetController(UserService userService) {        
        this.userService = userService;        
    }    
	
    @GetMapping("/passwordReset")
    public String passwordReset(Model model) {        
        model.addAttribute("passwordResetForm", new PasswordResetForm());
        return "auth/passwordReset";
    }  
	 
	 @PostMapping("/passwordReset")
	    public String signup(@ModelAttribute @Validated SignupForm signupForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {      
	        // メールアドレスが登録済みでなければ、BindingResultオブジェクトにエラー内容を追加する
	        if (!userService.isEmailRegistered(signupForm.getEmail())) {
	            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "登録されていないメールアドレスです。");
	            bindingResult.addError(fieldError);                       
	        }    
	        
	        if (bindingResult.hasErrors()) {
	            return "auth/passwordReset";
	        }
	        
	        userService.create(signupForm);
	        redirectAttributes.addFlashAttribute("successMessage", "パスワードリセットが完了しました。");

	        return "redirect:/";
	    }    
}
