package com.ping.wu.service.impl;

import com.ping.wu.exception.MyRejectedExecutionHandler;
import com.ping.wu.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author wuping
 * @date 2018/7/31
 */

@Service
public class TestServiceImpl implements TestService {
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private final ThreadPoolTaskExecutor taskExecutor;
    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Autowired
    public TestServiceImpl(ThreadPoolTaskScheduler threadPoolTaskScheduler,
                           ThreadPoolTaskExecutor taskExecutor) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public int test(int i) {
        try {
            taskExecutor.submit(() -> {
                LOGGER.info("task start!!!!!!");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (Exception e) {

                }
                LOGGER.info("task :" + i + " complete!");
            });
        } catch (RejectedExecutionException e) {
            throw new RejectedExecutionException(i + "");
        }
        return i + 1;
    }
}
