package com.example.nagoyameshi.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.event.PasswordResetEventPublisher;
import com.example.nagoyameshi.form.PasswordResetForm;
import com.example.nagoyameshi.service.UserService;
import com.example.nagoyameshi.service.VerificationTokenService;

@Controller
public class PasswordResetController {
	private final UserService userService;
	private final PasswordResetEventPublisher passwordResetEventPublisher;
	private final VerificationTokenService verificationTokenService;

	public PasswordResetController(UserService userService, PasswordResetEventPublisher passwordResetEventPublisher, VerificationTokenService verificationTokenService) {
		this.userService = userService;
		this.passwordResetEventPublisher = passwordResetEventPublisher;
		this.verificationTokenService = verificationTokenService;
	}

	@GetMapping("/passwordReset")
	public String passwordReset(Model model) {
		model.addAttribute("passwordResetForm", new PasswordResetForm());
		return "auth/passwordReset";
	}

	@PostMapping("/passwordReset")
	    public String passwordReset(@ModelAttribute @Validated PasswordResetForm passwordResetForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {      
	        if (!userService.isEmailRegistered(passwordResetForm.getEmail())) {
	            FieldError fieldError = new FieldError(bindingResult.getObjectName(), "email", "登録されていないメールアドレスです。");
	            bindingResult.addError(fieldError);                       
	        }    
	        
	        if(!userService.isSamePassword(passwordResetForm.getPassword(), passwordResetForm.getPasswordConfirmation())) {
				FieldError fieldError = new FieldError(bindingResult.getObjectName(), "password", "パスワードが一致しません。");
				bindingResult.addError(fieldError);
	        }
	        
	        if (bindingResult.hasErrors()) {
	            return "auth/passwordReset";
	        }
	        
	        User createdUser = userService.getUserByEmail(passwordResetForm);
	        String requestUrl = new String(httpServletRequest.getRequestURL());
	        passwordResetEventPublisher.publishPasswordResetEvent(createdUser, requestUrl);
	        redirectAttributes.addFlashAttribute("successMessage", "ご入力いただいたメールアドレスに認証メールを送信しました。メールに記載されているリンクをクリックし、パスワードリセットを完了してください。");        
	        
	        return "redirect:/";
	    }

	@GetMapping("/passwordReset/verifyPassword")
	public String verifyPassword(@RequestParam(name = "token") String token, Model model) {
		VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);

		if (verificationToken != null) {
			User user = verificationToken.getUser();
			userService.enableUser(user);
			String successMessage = "パスワードリセットが完了しました。";
			model.addAttribute("successMessage", successMessage);
		} else {
			String errorMessage = "トークンが無効です。";
			model.addAttribute("errorMessage", errorMessage);
		}

		return "auth/verifyPassword";
	}
}
