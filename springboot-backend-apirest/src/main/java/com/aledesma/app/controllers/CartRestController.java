package com.aledesma.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aledesma.app.models.services.AuthenticatedUserService;
import com.aledesma.app.models.services.ICartService;

@RestController
@RequestMapping(path = "/customer/{customerId}/cart")
public class CartRestController {
	
	@Autowired
	ICartService cartService;
	
	@Autowired
	private AuthenticatedUserService  authenticatedUserService;
	
	@GetMapping(path = "")
	@PreAuthorize("@authenticatedUserService.hasCustomerId(#customerId)")
	public ResponseEntity<?> showCustomerCart(@PathVariable Long customerId) {
		return cartService.getCart(customerId);
	}
	
	@PostMapping(path = "/add-item/{productId}")
	@PreAuthorize("@authenticatedUserService.hasCustomerId(#customerId)")
	public ResponseEntity<?> addItemToCart(@PathVariable Long customerId,@PathVariable Long productId) {
		return cartService.addItemToCart(customerId,productId);
	}
	
	@PutMapping(path = "/modify-item/{itemId}")
	@PreAuthorize("@authenticatedUserService.hasCustomerId(#customerId)")
	public ResponseEntity<?> modifyItemQuantity(@PathVariable Long customerId,@PathVariable Long itemId,@RequestParam(required = true) int quantity) {
		return cartService.modifyItemQuantity(customerId,itemId,quantity);
	}
	
	@DeleteMapping(path = "/remove-item/{itemId}")
	@PreAuthorize("@authenticatedUserService.hasCustomerId(#customerId)")
	public ResponseEntity<?> removeCartItem(@PathVariable Long customerId,@PathVariable Long itemId) {
		return cartService.removeCartItem(customerId,itemId);
	}
	
	@DeleteMapping(path = "/clear")
	@PreAuthorize("@authenticatedUserService.hasCustomerId(#customerId)")
	public ResponseEntity<?> deleteEntireCart(@PathVariable Long customerId) {
		return cartService.deleteCart(customerId);
	}
}
