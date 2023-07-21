package com.joe.springjpaexample.repo;

import com.joe.springjpaexample.domain.ProductSpec;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface ProductSpecRepository extends CrudRepository<ProductSpec, Long> ,JpaSpecificationExecutor<ProductSpec>{
	
	@Query("FROM ProductSpec s where s.product.id = :productId")
	Set<ProductSpec> findAllByProductId(@Param("productId") Long productId);
	
	@Query("FROM ProductSpec s where s.product.id > :productId order by s.product.id")
	Set<ProductSpec> findAllWihtProductIdGreater(@Param("productId") Long productId);



}
