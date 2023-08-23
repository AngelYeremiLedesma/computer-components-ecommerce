package com.aledesma.app.models.services;

import java.util.Optional;

import com.aledesma.app.models.entity.ConfirmationToken;
import com.aledesma.app.models.entity.UserEntity;

public interface IConfirmationTokenService {

	ConfirmationToken generateConfirmationToken(UserEntity userEntity);
	
	void saveConfirmationToken(ConfirmationToken confirmationToken); 
	
	Optional<ConfirmationToken> getConfirmationToken(String token);

	void confirmEmail(String token);
}
