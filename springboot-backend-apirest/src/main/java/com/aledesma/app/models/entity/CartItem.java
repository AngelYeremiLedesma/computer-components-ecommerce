package com.aledesma.app.models.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="cart_items")
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	private int quantity;
	private BigDecimal unitaryPrice;
	private BigDecimal subtotal;
	
	@PrePersist
	@PreUpdate
	public void calculateSubtotal() {
		this.unitaryPrice = this.product.getPrice();
		this.subtotal = this.unitaryPrice.multiply(BigDecimal.valueOf(this.quantity));
	}
	
}
