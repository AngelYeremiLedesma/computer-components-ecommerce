package com.aledesma.app.models.services;

import org.springframework.http.ResponseEntity;

public interface ICartService {

	public ResponseEntity<?> getCart(Long customerId);
	
	public ResponseEntity<?> addItemToCart(Long customerId,Long productId);
	
	public ResponseEntity<?> modifyItemQuantity(Long customerId,Long itemId, int quantity);
	
	public ResponseEntity<?> removeCartItem(Long customerId,Long itemId);
	
	public ResponseEntity<?> deleteCart(Long customerId);

}
