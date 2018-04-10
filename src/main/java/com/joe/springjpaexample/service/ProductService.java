package com.joe.springjpaexample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.repo.ProductRepository;

@Service
public class ProductService {
	@Autowired
	private ProductRepository productRepo;
	
	/**
	 * Specification usage example
	 * @param caseNo
	 * @return
	 */
	public List<Product> find(String caseNo){
		return productRepo.findAll(MySpecifications.joinProductSpec(caseNo));
	}
}
