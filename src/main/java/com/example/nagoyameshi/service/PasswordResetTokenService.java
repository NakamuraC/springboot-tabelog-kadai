package com.example.nagoyameshi.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.PasswordResetToken;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenService {
	private final PasswordResetTokenRepository PasswordResetTokenRepository;
    
    
    public PasswordResetTokenService(PasswordResetTokenRepository PasswordResetTokenRepository) {        
        this.PasswordResetTokenRepository = PasswordResetTokenRepository;
    } 
    
    @Transactional
    public void create(User user, String token) {
        PasswordResetToken PasswordResetToken = new PasswordResetToken();
        
        PasswordResetToken.setUser(user);
        PasswordResetToken.setToken(token);        
        
        PasswordResetTokenRepository.save(PasswordResetToken);
    }    
    
    public PasswordResetToken getPasswordResetToken(String token) {
        return PasswordResetTokenRepository.findByToken(token);
    }    
}
