package com.aledesma.app.models.entity;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="products")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String description;
	private String brand;
	private BigDecimal price;
	private String imageURL;
	private int availableStock;
	private boolean isAvailable;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "product_id")
	private List<Specification> specificationsList;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "category_id")
	private Category category;
	
	
}
