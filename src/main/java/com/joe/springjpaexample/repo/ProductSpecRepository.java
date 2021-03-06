package com.joe.springjpaexample.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.joe.springjpaexample.domain.ProductSpec;


@Repository
public interface ProductSpecRepository extends CrudRepository<ProductSpec, Long> ,JpaSpecificationExecutor<ProductSpec>{
	
	@Query("FROM ProductSpec s where s.product.id = :productId")
	Set<ProductSpec> findAllByProductId(@Param("productId") Long productId);
	
	@Query("FROM ProductSpec s where s.product.id > :productId order by s.product.id, id")
	Set<ProductSpec> findAllWihtProductIdGreater(@Param("productId") Long productId);
	
}
