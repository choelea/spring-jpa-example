package com.joe.springjpaexample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.service.ProductService;

@RestController
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<Product> find(@RequestParam String caseNo){
		return productService.find(caseNo);
	}
}
