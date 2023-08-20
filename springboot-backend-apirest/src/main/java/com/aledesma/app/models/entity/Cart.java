package com.aledesma.app.models.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="carts")
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "cart_id")
	private List<CartItem> items;
	
	private BigDecimal total = BigDecimal.ZERO;
	
	@PrePersist
	@PreUpdate
	public void recalculateTotal() {
		if(this.items!=null) {
			total = items.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
		}else {
			total = BigDecimal.ZERO;
		}
	}
	
}
