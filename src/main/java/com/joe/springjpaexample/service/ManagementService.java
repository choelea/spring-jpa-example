package com.joe.springjpaexample.service;

import com.joe.springjpaexample.domain.Category;
import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.repo.CategoryRepository;
import com.joe.springjpaexample.repo.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;

/**
 * @author : Joe
 * @date : 2022/3/28
 */
@Service
@Slf4j
public class ManagementService {
    private ProductRepository pRepo;
    private CategoryRepository cateRepo;

    public ManagementService(ProductRepository pRepo, CategoryRepository cateRepo) {
        this.pRepo = pRepo;
        this.cateRepo = cateRepo;
    }


    @Async
    public void initData() {
        log.info("################################### Started Initiating Data ##################################");
        createCategories();
        addProduct();
        log.info("################################### End Initiating Data ##################################");
    }

    private void createCategories() {
        Category cate = new Category();
        cate.setName("Category 1");
        cate.setCode("cate-1");
        cateRepo.save(cate);

        cate = new Category();
        cate.setName("Category 2");
        cate.setCode("cate-2");
        cateRepo.save(cate);

        cate = new Category();
        cate.setName("Category 3");
        cate.setCode("cate-3");
        cateRepo.save(cate);

        cate = new Category();
        cate.setName("Category 4");
        cate.setCode("cate-4");
        cateRepo.save(cate);
    }

    /**
     * For many to many relations, needs to be aware of 'bidirectional consistency problem'. Learn more from below link:
     * https://stackoverflow.com/questions/13370221/jpa-hibernate-detached-entity-passed-to-persist
     */
    @Transactional
    public void addProduct() {
        Category cate1 = cateRepo.getByCode("cate-1");
        Category cate2 = cateRepo.getByCode("cate-2");
        Category cate3 = cateRepo.getByCode("cate-3");
        Category cate4 = cateRepo.getByCode("cate-4");

        Product product;
        for (int i = 0; i < 300000; i++) {
            product = new Product();
            product.setName("product "+i);
            product.setCode("p-"+i);
            product.setPrice(19L);
            product.setCategories(new HashSet<>());
            product.getCategories().add(cate1);
            product.getCategories().add(cate2);
            pRepo.save(product);
        }

        for (int i = 300000; i < 800000; i++) {
            product = new Product();
            product.setName("product "+i);
            product.setCode("p-"+i);
            product.setPrice(19L);
            product.setCategories(new HashSet<>());
            product.getCategories().add(cate1);
            product.getCategories().add(cate3);
            pRepo.save(product);
        }

        for (int i = 800000; i < 1000000; i++) {
            product = new Product();
            product.setName("product "+i);
            product.setCode("p-"+i);
            product.setPrice(19L);
            product.setCategories(new HashSet<>());
            product.getCategories().add(cate3);
            product.getCategories().add(cate2);
            pRepo.save(product);
        }

        for (int i = 0; i < 20; i++) {
            product = new Product();
            product.setName("iphone");
            product.setCode("pp-"+i);
            product.setPrice(19L);
            product.setCategories(new HashSet<>());
            product.getCategories().add(cate4);
            product.getCategories().add(cate2);
            pRepo.save(product);
        }

    }
}
