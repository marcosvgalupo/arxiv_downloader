package com.searchonmath.arxivdownloader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories
@EnableAsync(proxyTargetClass = true)
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class ArxivDownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArxivDownloaderApplication.class, args);
	}

}
