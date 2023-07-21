package com.joe.springjpaexample.controller;

import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;


	@GetMapping("/products/{id}")
	public Product find(@PathVariable Long id){
		return productService.get(id);
	}

	@GetMapping("/products")
	public List<Product> find(@RequestParam(required = false) String caseNo){
		return productService.find(caseNo);
	}
}
