package com.searchonmath.arxivprocessor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {
    @Value("${threads.page.prefix}")
    private String pagePoolPrefix;

    @Value("${threads.page.max}")
    private Integer pagePoolMax;

    @Value("${threads.page.core}")
    private Integer pagePoolCore;

    @Value("${threads.page.keepAlive}")
    private Integer pagePoolKeepAlive;

    @Bean("pagePool")
    public TaskExecutor pagePoolAsyncConfig(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(pagePoolPrefix);
        executor.setCorePoolSize(pagePoolCore);
        executor.setMaxPoolSize(pagePoolMax);
        executor.setKeepAliveSeconds(pagePoolKeepAlive);
        executor.setThreadPriority(Thread.MAX_PRIORITY);
        return executor;
    }

}
