package com.searchonmath.arxivdownloader.config.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ArxivThreadConfig {

    @Bean("arxivThreadPool")
    public TaskExecutor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("ARXIV-");
        executor.setCorePoolSize(100);
        executor.setMaxPoolSize(100);
        executor.setKeepAliveSeconds(1);
        return executor;
    }
}
