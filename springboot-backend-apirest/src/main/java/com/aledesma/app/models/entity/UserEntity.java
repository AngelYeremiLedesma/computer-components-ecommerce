package com.aledesma.app.models.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="users")
public class UserEntity{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	@NotBlank
	@Size(min = 8,max = 20)
	private String username;
	
	@Column(unique=true)
	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Size(max = 68)
	private String password;
	
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinTable(name = "users_roles",
				joinColumns = @JoinColumn(name="user_id"),
				inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<Role> roleList;
	
	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

}