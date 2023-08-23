package com.aledesma.app.models.repositories;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.aledesma.app.models.entity.ConfirmationToken;

import jakarta.transaction.Transactional;

public interface IConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long>{

	public Optional<ConfirmationToken> findByToken(String token);
	
    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c SET c.confirmedAt = ?2 WHERE c.token = ?1")
    int updateConfirmedAt(String token, LocalDateTime confirmedAt);
}
