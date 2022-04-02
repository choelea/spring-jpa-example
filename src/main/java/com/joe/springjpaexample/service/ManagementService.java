package com.joe.springjpaexample.service;

import com.joe.springjpaexample.domain.Category;
import com.joe.springjpaexample.domain.Product;
import com.joe.springjpaexample.repo.CategoryRepository;
import com.joe.springjpaexample.repo.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author : Joe
 * @date : 2022/3/28
 */
@Service
@Slf4j
public class ManagementService {
    private ProductRepository pRepo;
    private CategoryRepository cateRepo;
    private ThreadPoolTaskExecutor taskExecutor;
    public ManagementService(ProductRepository pRepo, CategoryRepository cateRepo, ThreadPoolTaskExecutor taskExecutor) {
        this.pRepo = pRepo;
        this.cateRepo = cateRepo;
        this.taskExecutor = taskExecutor;
    }

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
//    @Transactional
    public void addProduct() {

        Category cate1 = cateRepo.getByCode("cate-1");
        Category cate2 = cateRepo.getByCode("cate-2");
        Category cate3 = cateRepo.getByCode("cate-3");
        Category cate4 = cateRepo.getByCode("cate-4");
        List<Category> categories = new ArrayList<>(4);
        categories.add(cate1);
        categories.add(cate2);
        categories.add(cate3);
        categories.add(cate4);

        int batchSize = 10000;
        for (int i=0; i < 80; i++) {
            int finalI = i;
            taskExecutor.execute(()->{
                Category c1 = categories.get(RandomUtils.nextInt(0, 4));
                Category c2 = categories.get(RandomUtils.nextInt(0, 4));
                insertProducts(finalI *batchSize, batchSize, c1, c2);
                log.info("Current Thread {} finished", Thread.currentThread().getId()+Thread.currentThread().getName());
            });
        }

        log.info(""+taskExecutor.getActiveCount());
        log.info(""+taskExecutor.getCorePoolSize());
        log.info(""+taskExecutor.getMaxPoolSize());
        log.info(""+taskExecutor.getPoolSize());
    }


    private void insertProducts(int startIndex, int batchSize, Category cate1, Category cate2){
        List<Product> list = new ArrayList<>(batchSize);
        Product product;
        int end = startIndex + batchSize;
        for (int i = startIndex; i < end; i++) {
            product = new Product();
            product.setName("product "+i);
            product.setCode("p-"+i);
            product.setPrice(19L);
            product.setCategories(new HashSet<>());
            product.getCategories().add(cate1);
            product.getCategories().add(cate2);
            list.add(product);
        }
        pRepo.saveAll(list);//经测试， 效率要高很多
    }
}
