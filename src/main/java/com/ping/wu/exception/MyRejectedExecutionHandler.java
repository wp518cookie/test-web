package com.ping.wu.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wuping
 * @date 2018/8/6
 */

public class MyRejectedExecutionHandler implements RejectedExecutionHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(MyRejectedExecutionHandler.class);
    public MyRejectedExecutionHandler() {

    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            throw new RejectedExecutionException();
        }
    }
}
