package com.aledesma.app.models.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.aledesma.app.exceptions.CartItemNotFoundException;
import com.aledesma.app.exceptions.CartNotFoundException;
import com.aledesma.app.exceptions.CustomerNotFoundException;
import com.aledesma.app.exceptions.ProductNotFoundException;
import com.aledesma.app.models.entity.Cart;
import com.aledesma.app.models.entity.CartItem;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.entity.Product;
import com.aledesma.app.models.repositories.ICartRepository;
import com.aledesma.app.models.repositories.ICartItemRepository;
import com.aledesma.app.models.repositories.ICustomerRepository;
import com.aledesma.app.models.repositories.IProductRepository;

@Service
public class CartServiceImpl implements ICartService {

	@Autowired
	ICartRepository cartRepository;

	@Autowired
	ICustomerRepository customerRepository;

	@Autowired
	IProductRepository productRepository;

	@Autowired
	ICartItemRepository cartItemRepository;

	@Override
	public ResponseEntity<?> getCart(Long customerId) {
		Map<String, Object> response = new HashMap<>();

		Cart cart = findCartByCustomerId(customerId);
		response.put("cart", cart);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> addItemToCart(Long customerId, Long productId) {
		Map<String, Object> response = new HashMap<>();

		Cart cart = findCartByCustomerId(customerId);
		List<CartItem> cartItems = cart.getItems();
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ProductNotFoundException("Product Not Found"));

		CartItem cartItem = cartItems.stream().filter((item) -> {
			return product.equals(item.getProduct());
		}).findFirst().orElse(null);

		if (cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity() + 1);
		} else {
			cartItem = CartItem.builder().product(product).quantity(1).build();
			cartItems.add(cartItem);
		}
		cartItemRepository.save(cartItem);

		cart.setItems(cartItems);
		cart.recalculateTotal();
		cartRepository.save(cart);

		response.put("cart", cart);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> modifyItemQuantity(Long customerId, Long itemId, int quantity) {
		Map<String, Object> response = new HashMap<>();

		Cart cart = findCartByCustomerId(customerId);
		List<CartItem> cartItems = cart.getItems();
		CartItem cartItem = cartItems.stream().filter((item) -> {
			return item.getId().equals(itemId);
		}).findFirst().orElseThrow(() -> new CartItemNotFoundException("Cart Item Not Found"));
		cartItem.setQuantity(quantity);

		cartItemRepository.save(cartItem);
		cart = findCartByCustomerId(customerId);
		cart.recalculateTotal();
		cartRepository.save(cart);

		response.put("cart", cart);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> removeCartItem(Long customerId, Long itemId) {
		Map<String, Object> response = new HashMap<>();

		Cart cart = findCartByCustomerId(customerId);
		List<CartItem> cartItemsInitial = cart.getItems();
		Long itemCoincidences = cartItemsInitial.stream().filter((item)->{
			return item.getId() == itemId;
		}).count();
		
		if(itemCoincidences==0L) {
			throw new CartItemNotFoundException("Cart Item Not Found");
		}
		
		List<CartItem> cartItemsDeleted = cartItemsInitial.stream().filter((item) -> {
			return item.getId() != itemId;
		}).collect(Collectors.toList());
		cartItemRepository.deleteById(itemId);
		cart.setItems(cartItemsDeleted);
		cart.recalculateTotal();
		cartRepository.save(cart);

		response.put("cart", cart);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> deleteCart(Long customerId) {
		Map<String, Object> response = new HashMap<>();

		Cart cart = findCartByCustomerId(customerId);
		Long cartId = cart.getId();
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
		Cart newCart = Cart.builder().build();
		customer.setCart(newCart);
		cartRepository.save(newCart);
		customerRepository.save(customer);
		cartRepository.deleteById(cartId);
		response.put("message", "Cart Deleted Correctly");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	public Cart findCartByCustomerId(Long customerId) {
		Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));
		Cart cart = cartRepository.findById(customer.getCart().getId()).orElseThrow(() -> new CartNotFoundException("Cart Not Found"));
		return cart;
	}

}
