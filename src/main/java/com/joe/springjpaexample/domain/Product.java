package com.joe.springjpaexample.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Product {
	/**
     * define User id
     */
    @Id
    @Column(length = 20)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50,unique=true)
    private String code;
    
    private String name;
    
    private Long price;
    
    @ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "product_category", joinColumns = { @JoinColumn(name = "productId", referencedColumnName = "id") }, inverseJoinColumns = {
			@JoinColumn(name = "categoryId", referencedColumnName = "id") })    
    @JsonIgnore
    private Set<Category> categories;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ProductSpec> productSpecs;
    
    
    public Set<ProductSpec> getProductSpecs() {
		return productSpecs;
	}

	public void setProductSpecs(Set<ProductSpec> productSpecs) {
		this.productSpecs = productSpecs;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

    
    
}
