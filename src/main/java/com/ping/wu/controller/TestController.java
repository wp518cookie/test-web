package com.ping.wu.controller;

import com.ping.wu.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author wuping
 * @date 2018/7/11
 */

@Controller
@RequestMapping("/test")
public class TestController {
    private final TestService testService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String appName = request.getParameter("appName");
        System.out.println(appName);
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        System.out.println(sb);
    }

    @GetMapping
    @ResponseBody
    public String testGet() {
        new Thread(() -> {
            int i = 0;
            while (true) {
                try {
                    i = testService.test(i);
                    if (i == 10) {
                        break;
                    }
                } catch (RejectedExecutionException e) {
                    LOGGER.info("任务已满，休眠后继续执行任务: " + e.getMessage());
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    i = Integer.parseInt(e.getMessage());
                }
            }
        }).start();
        return "success";
    }
}
