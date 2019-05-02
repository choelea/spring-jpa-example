package com.joe.springjpaexample.repo;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.joe.springjpaexample.domain.Category;


@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> ,JpaSpecificationExecutor<Category>{
	
	Category getByCode(String code);
	
}