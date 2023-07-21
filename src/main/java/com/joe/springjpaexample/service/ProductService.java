package com.joe.springjpaexample.service;

import com.joe.springjpaexample.domain.ProdSpecView;
import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.repo.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.FatalBeanException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import javax.persistence.TypedQuery;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
	private ProductRepository productRepo;

	private EntityManager entityManager;

	public ProductService(ProductRepository productRepo, EntityManager entityManager) {
		this.productRepo = productRepo;
		this.entityManager = entityManager;
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
		if(caseNo == null){
			Page<Product> all = productRepo.findAll(Pageable.ofSize(10));
			return all.getContent();
		} else {
			return productRepo.findAll(MySpecifications.joinProductSpec(caseNo));
		}
	}

	/**
	 * https://vladmihalcea.com/the-best-way-to-map-a-projection-query-to-a-dto-with-jpa-and-hibernate/
	 * @param productId
	 * @return
	 */
	public List<ProdSpecView> findSpecsWithProductName(Long productId) {
		final TypedQuery<Tuple> query = entityManager.createQuery("""
					SELECT ps.code as code, p.id as id, ps.value as value, p.name as name 
					FROM Product p  LEFT JOIN ProductSpec ps ON ps.product = p WHERE p.id = :productId
				""", Tuple.class);

		query.setParameter("productId", productId);
		final List<Tuple> resultList = query.getResultList();
		List<ProdSpecView> targetList = new ArrayList<>();
		if(!resultList.isEmpty()){
			resultList.forEach(tuple -> {
				ProdSpecView prodSpecView = new ProdSpecView();
				copy(tuple, prodSpecView);
				targetList.add(prodSpecView);
			});
		}
		return targetList;
	}

	public void copy(Tuple source, Object target,@Nullable String... ignoreProperties){
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);
		final List<TupleElement<?>> elements = source.getElements();
		elements.forEach(tupleElement -> {
			final String alias = tupleElement.getAlias();
			final PropertyDescriptor targetPd = BeanUtils.getPropertyDescriptor(target.getClass(), alias);
			if(targetPd!=null){
				Method writeMethod = targetPd.getWriteMethod();
				if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
					try {
						Object value = source.get(targetPd.getName());
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException(
								"Could not copy property '" + targetPd.getName() + "' from source to target", ex);
					}
				}
			}
		});
	}
}
