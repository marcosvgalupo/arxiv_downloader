package com.searchonmath.arxivdownloader.config.application;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "config.execution-properties")
@Configuration
public class ExecutionProperties {

    private Integer downloadResult;
    private Integer threadNumber;
    private Integer chunkSize;
    private Integer sleep;

}
