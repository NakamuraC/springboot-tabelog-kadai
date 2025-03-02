package com.example.nagoyameshi.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.service.PasswordResetTokenService;

@Component
public class PasswordResetEventListener {
	private final PasswordResetTokenService passwordResetTokenService;    
    private final JavaMailSender javaMailSender;
    
    public PasswordResetEventListener(PasswordResetTokenService passwordResetTokenService, JavaMailSender mailSender) {
        this.passwordResetTokenService = passwordResetTokenService;        
        this.javaMailSender = mailSender;
    }

    @EventListener
    private void onPasswordResetEvent(PasswordResetEvent passwordResetEvent) {
        User user = passwordResetEvent.getUser();
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.create(user, token);
        
        String senderAddress = "springboot.nagoyameshi@example.com";
        String recipientAddress = user.getEmail();
        String subject = "パスワードリセット";
        String confirmationUrl = passwordResetEvent.getRequestUrl() + "/verify?token=" + token;
        String message = "以下のリンクをクリックしてパスワードリセットを完了してください。";
        
        SimpleMailMessage mailMessage = new SimpleMailMessage(); 
        mailMessage.setFrom(senderAddress);
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\n" + confirmationUrl);
        javaMailSender.send(mailMessage);
    }
}
