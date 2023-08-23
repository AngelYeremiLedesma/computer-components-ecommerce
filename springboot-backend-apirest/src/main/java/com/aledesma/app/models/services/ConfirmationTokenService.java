package com.aledesma.app.models.services;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aledesma.app.models.entity.ConfirmationToken;
import com.aledesma.app.models.entity.UserEntity;
import com.aledesma.app.models.repositories.IConfirmationTokenRepository;

@Service
public class ConfirmationTokenService implements IConfirmationTokenService{
	
	@Autowired
	IConfirmationTokenRepository confirmationTokenRepository;
	
	@Override
	public ConfirmationToken generateConfirmationToken(UserEntity user) {
		String token = UUID.randomUUID().toString();
		LocalDateTime createdAt = LocalDateTime.now();
		LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(15);
		
		ConfirmationToken confirmationToken = ConfirmationToken.builder().token(token).createdAt(createdAt).expiresAt(expiresAt).user(user).build();
		return confirmationToken;
	}

	@Override
	public void saveConfirmationToken(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
	}

	@Override
	public Optional<ConfirmationToken> getConfirmationToken(String token) {
		return confirmationTokenRepository.findByToken(token);
	}

	@Override
	public void confirmEmail(String token) {
        confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
	}

}
