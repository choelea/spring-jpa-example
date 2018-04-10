package com.joe.springjpaexample.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.joe.springjpaexample.domain.Product;


@Repository
public interface ProductRepository extends CrudRepository<Product, Long> ,JpaSpecificationExecutor<Product>{
	
	Product getByCode(String code);
	
}
