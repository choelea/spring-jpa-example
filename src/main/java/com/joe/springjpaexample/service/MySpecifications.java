package com.joe.springjpaexample.service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.domain.ProductSpec;
import com.joe.springjpaexample.domain.ProductSpec_;
import com.joe.springjpaexample.domain.Product_;

@SuppressWarnings("serial")
public class MySpecifications {

	/**
	 * Will join ProductSpec on conditions that caseNo is same as given in parameters
	 * @param caseNo
	 * @return
	 */
	public static Specification<Product> joinProductSpec(String caseNo) {
		return new Specification<Product>() {
			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Join<Product, ProductSpec> join = root.join(Product_.productSpecs);
				Predicate predicate = join.getOn();
				if(!StringUtils.isEmpty(caseNo)){
					predicate = cb.and(cb.equal(join.get(ProductSpec_.code), "caseNo"), cb.equal(join.get(ProductSpec_.value), caseNo));
				}
	            return predicate;
			}
		};
	}
	

	
}
