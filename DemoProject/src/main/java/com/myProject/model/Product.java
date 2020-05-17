package com.myProject.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 20)
	private String name;
	
	@NotBlank
	@Size(max = 100)
	private String description;
	
	@NotBlank
	@Size(max = 100)
	private String imagePath;
	
	private boolean isActiveProduct;

	public Product() {
		
	}

	public Long getId() {
		return id;
	}

	public boolean isActiveProduct() {
		return isActiveProduct;
	}

	public void setActiveProduct(boolean isActiveProduct) {
		this.isActiveProduct = isActiveProduct;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
}
