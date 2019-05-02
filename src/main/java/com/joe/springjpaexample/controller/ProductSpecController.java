package com.joe.springjpaexample.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joe.springjpaexample.domain.ProductSpec;
import com.joe.springjpaexample.repo.ProductSpecRepository;

@RestController
public class ProductSpecController {
	@Autowired
	private ProductSpecRepository productSpecRepository;
	
	@GetMapping("/{productId}/productspecs")
	public Set<ProductSpec> find(@PathVariable Long productId){
		return productSpecRepository.findAllByProductId(productId);
	}
	
	@GetMapping("/productspecs")
	public Set<ProductSpec> findAllWihtProductIdGreater(@RequestParam Long minProductId){
		return productSpecRepository.findAllWihtProductIdGreater(minProductId);
	}
}
