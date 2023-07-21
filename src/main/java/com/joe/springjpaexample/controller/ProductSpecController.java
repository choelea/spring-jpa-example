package com.joe.springjpaexample.controller;

import com.joe.springjpaexample.domain.ProdSpecView;
import com.joe.springjpaexample.domain.ProductSpec;
import com.joe.springjpaexample.repo.ProductSpecRepository;
import com.joe.springjpaexample.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ProductSpecController {
	private ProductSpecRepository productSpecRepository;

	private ProductService productService;

	public ProductSpecController(ProductSpecRepository productSpecRepository, ProductService productService) {
		this.productSpecRepository = productSpecRepository;
		this.productService = productService;
	}

	@GetMapping("/{productId}/productspecs")
	public Set<ProductSpec> find(@PathVariable Long productId){
		return productSpecRepository.findAllByProductId(productId);
	}

	@GetMapping("/{productId}/specviews")
	public List<ProdSpecView> findViews(@PathVariable Long productId){
		return productService.findSpecsWithProductName(productId);
	}
	
	@GetMapping("/productspecs")
	public Set<ProductSpec> findAllWihtProductIdGreater(@RequestParam Long minProductId){
		return productSpecRepository.findAllWihtProductIdGreater(minProductId);
	}
}
