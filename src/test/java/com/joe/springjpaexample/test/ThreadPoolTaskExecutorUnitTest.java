package com.joe.springjpaexample.test;

/**
 * 出自： https://www.baeldung.com/java-threadpooltaskexecutor-core-vs-max-poolsize
 * @date : 2022/4/1
 */

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

public class ThreadPoolTaskExecutorUnitTest {

    void startThreads(ThreadPoolTaskExecutor taskExecutor, CountDownLatch countDownLatch, int numThreads) {
        for (int i = 0; i < numThreads; i++) {
            int index = i;
            taskExecutor.execute(() -> {
                try {
                    System.out.println("Started Threads: "+index);
                    Thread.sleep(100L * ThreadLocalRandom.current().nextLong(1, 10));
                    System.out.println("This is Threads: "+index);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    /**
     * 默认的时候corePoolSize=1， maxPoolSize和queueCapacity无限制, 始终只有一个线程
     */
    @Test
    public void whenUsingDefaults_thenSingleThread() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.afterPropertiesSet();
        log(taskExecutor);

        int numThreads = 10;
        CountDownLatch countDownLatch = new CountDownLatch(numThreads);
        this.startThreads(taskExecutor, countDownLatch, numThreads);

        while (countDownLatch.getCount() > 0) {
            Assert.assertEquals(1, taskExecutor.getPoolSize());
        }
    }

    /**
     * maxPoolSize + queueCapacity 满足不了线程数的需求的时候，任务会被拒绝，并抛出异常TaskRejectedException
     */
    @Test
    public void whenMaxPoolSizePlusQueueCapacity_lessThan_required_thenGotException() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(5);
        taskExecutor.afterPropertiesSet();

        int numThreads = 16;
        CountDownLatch countDownLatch = new CountDownLatch(numThreads);
        try{
            this.startThreads(taskExecutor, countDownLatch, numThreads);
        }catch (Exception e){
            Assert.assertTrue(e instanceof TaskRejectedException);
        }
    }

    /**
     * 当queueCapacity + corePoolSize 满足需求线程数的时候，是不会去创建corePoolSize数量外的线程的。
      */
    @Test
    public void whenCorePoolSizeFiveAndMaxPoolSizeTen_thenFiveThreads() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.afterPropertiesSet();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        this.startThreads(taskExecutor, countDownLatch, 10);

        while (countDownLatch.getCount() > 0) {
            Assert.assertEquals(5, taskExecutor.getPoolSize());
        }
    }

    /**
     * 放弃队列缓冲的时候(queueCapacity=0)，会根据需要创建 <=maxPoolSize数量的线程
     */
    @Test
    public void whenCorePoolSizeFiveAndMaxPoolSizeTenAndQueueCapacityZero_thenTenThreads() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(0);
        taskExecutor.afterPropertiesSet();

        CountDownLatch countDownLatch = new CountDownLatch(10);
        this.startThreads(taskExecutor, countDownLatch, 10);

        while (countDownLatch.getCount() > 0) {
            Assert.assertEquals(10, taskExecutor.getPoolSize());
        }
    }

    /**
     * corePoolSize +  queueCapacity < 需要线程数量的时候, 需要创建
     */
    @Test
    public void whenCorePoolSizeFiveAndMaxPoolSizeTenAndQueueCapacityTen_thenTenThreads() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(10);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.afterPropertiesSet();

        CountDownLatch countDownLatch = new CountDownLatch(20);
        this.startThreads(taskExecutor, countDownLatch, 20);
        while (countDownLatch.getCount() > 0) {
            Assert.assertEquals(10, taskExecutor.getPoolSize());
        }
    }


    private void log(ThreadPoolTaskExecutor taskExecutor){
        System.out.println("Core Pool Size:" + taskExecutor.getCorePoolSize());
        System.out.println("Max Pool Size:" + taskExecutor.getMaxPoolSize());
    }
}