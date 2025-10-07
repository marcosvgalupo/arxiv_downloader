package com.searchonmath.arxivreader.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    @Value("${threads.readerPool.prefix}")
    private String readerPoolPrefix;

    @Value("${threads.readerPool.max}")
    private Integer readerPoolMax;

    @Value("${threads.readerPool.core}")
    private Integer readerPoolCore;

    @Value("${threads.readerPool.keepAlive}")
    private Integer readerPoolKeepAlive;

    @Value("${threads.folderPool.prefix}")
    private String folderPoolPrefix;

    @Value("${threads.folderPool.max}")
    private Integer folderPoolMax;

    @Value("${threads.folderPool.core}")
    private Integer folderPoolCore;

    @Value("${threads.folderPool.keepAlive}")
    private Integer folderPoolKeepAlive;


    @Bean("readerPool")
    public TaskExecutor readerPoolAsyncConfig(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(readerPoolPrefix);
        executor.setCorePoolSize(readerPoolCore);
        executor.setMaxPoolSize(readerPoolMax);
        executor.setKeepAliveSeconds(readerPoolKeepAlive);
        return executor;
    }

    @Bean("folderPool")
    public TaskExecutor folderPoolAsyncConfig(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix(folderPoolPrefix);
        executor.setCorePoolSize(folderPoolCore);
        executor.setMaxPoolSize(folderPoolMax);
        executor.setKeepAliveSeconds(folderPoolKeepAlive);
        return executor;
    }

}
