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
import com.aledesma.app.exceptions.CustomerNotFoundException;
import com.aledesma.app.exceptions.ProductNotFoundException;
import com.aledesma.app.models.dao.ICartDao;
import com.aledesma.app.models.dao.ICartItemDao;
import com.aledesma.app.models.dao.ICustomerDao;
import com.aledesma.app.models.dao.IProductDao;
import com.aledesma.app.models.entity.Cart;
import com.aledesma.app.models.entity.CartItem;
import com.aledesma.app.models.entity.Customer;
import com.aledesma.app.models.entity.Product;

@Service
public class CartServiceImpl implements ICartService{

	@Autowired
	ICartDao cartDao;
	
	@Autowired
	ICustomerDao customerDao;
	
	@Autowired
	IProductDao productDao;
	
	@Autowired
	ICartItemDao cartItemDao;
	
	@Override
	public ResponseEntity<?> getCart(Long customerId) {
		Map<String,Object> response = new HashMap<>();
		try {
			Cart cart = findCartByCustomerId(customerId);
			response.put("cart", cart);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}catch(DataAccessException e) {
			response.put("error", "Error accessing the DB");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(CustomerNotFoundException e) {
			response.put("error", "Customer Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> addItemToCart(Long customerId,Long productId) {
		Map<String,Object> response = new HashMap<>();
		try {
			
			Cart cart = findCartByCustomerId(customerId);
			List<CartItem> cartItems = cart.getItems();
			Product product = productDao.findById(productId).orElseThrow(()-> new ProductNotFoundException("Product Not Found"));
			
			CartItem cartItem = cartItems.stream().filter((item)-> {return product.equals(item.getProduct());}).findFirst().orElse(null);
			
			if(cartItem!=null) {
				cartItem.setQuantity(cartItem.getQuantity()+1);
			}else {
				cartItem = CartItem.builder().product(product).quantity(1).build();
				cartItems.add(cartItem);
			}
			cartItemDao.save(cartItem);
			
			cart.setItems(cartItems);
			cart.recalculateTotal();
			cartDao.save(cart);
			
			response.put("cart", cart);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}catch(ProductNotFoundException e) {
			response.put("error", "Product Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);			
		}catch(DataAccessException e) {
			response.put("error", "Error accessing the DB");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(CustomerNotFoundException e) {
			response.put("error", "Customer Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> modifyItemQuantity(Long customerId,Long itemId,int quantity) {
		Map<String,Object> response = new HashMap<>();
		try {
			Cart cart = findCartByCustomerId(customerId);
			List<CartItem> cartItems = cart.getItems();
			CartItem cartItem = cartItems.stream().filter((item)->{return item.getId().equals(itemId);}).findFirst().orElseThrow(()-> new CartItemNotFoundException("Cart Item Not Found"));
			cartItem.setQuantity(quantity);
			
			cartItemDao.save(cartItem);
			cart = findCartByCustomerId(customerId); 
			cart.recalculateTotal();
			cartDao.save(cart);
			 

			response.put("cart", cart);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}catch(CartItemNotFoundException e) {
			response.put("error", "Cart Item Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);	
		}catch(DataAccessException e) {
			response.put("error", "Error accessing the DB");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(CustomerNotFoundException e) {
			response.put("error", "Customer Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> removeCartItem(Long customerId,Long itemId) {
		Map<String,Object> response = new HashMap<>();
		try {
			Cart cart = findCartByCustomerId(customerId);
			List<CartItem> cartItemsInitial = cart.getItems();
			List<CartItem> cartItemsDeleted = cartItemsInitial.stream().filter((item)->{return item.getId()!=itemId;}).collect(Collectors.toList());
			cartItemDao.deleteById(itemId);
			cart.setItems(cartItemsDeleted);
			cart.recalculateTotal();
			cartDao.save(cart);
			 

			response.put("cart", cart);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}catch(CartItemNotFoundException e) {
			response.put("error", "Cart Item Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);	
		}catch(DataAccessException e) {
			response.put("error", "Error accessing the DB");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(CustomerNotFoundException e) {
			response.put("error", "Customer Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> deleteCart(Long customerId) {
		Map<String,Object> response = new HashMap<>();
		try {
			Cart cart = findCartByCustomerId(customerId);
			Long cartId = cart.getId();
			Customer customer = customerDao.findById(customerId).orElse(null);
			Cart newCart = Cart.builder().build();
			customer.setCart(newCart);
			cartDao.save(newCart);
			customerDao.save(customer);
			cartDao.deleteById(cartId);
			response.put("message", "Cart Deleted Correctly");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		}catch(DataAccessException e) {
			response.put("error", "Error accessing the DB");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}catch(CustomerNotFoundException e) {
			response.put("error", "Customer Not Found");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
	}
	
	public Cart findCartByCustomerId(Long customerId) {
		Customer customer = customerDao.findById(customerId).orElse(null);
		if(customer==null) {
			throw new CustomerNotFoundException("Customer Not Found");
		}
		Cart cart = cartDao.findById(customer.getCart().getId()).orElseThrow(() -> new RuntimeException(""));
		return cart;
	}

}
