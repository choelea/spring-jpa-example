package com.joe.springjpaexample.service;

import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.repo.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
	private ProductRepository productRepo;

	public ProductService(ProductRepository productRepo) {
		this.productRepo = productRepo;
	}

	public Product get(Long id){
		return productRepo.findById(id).get();
	}
	/**
	 * Specification usage example
	 * @param caseNo
	 * @return
	 */
	public List<Product> find(String caseNo){
		return productRepo.findAll(MySpecifications.joinProductSpec(caseNo));
	}
}
