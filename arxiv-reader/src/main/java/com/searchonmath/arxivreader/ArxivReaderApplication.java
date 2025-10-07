package com.searchonmath.arxivreader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
@EnableAsync(proxyTargetClass = true)
@ComponentScan(basePackages = {"com.searchonmath.arxivreader", "com.searchonmath.arxivglobal"})
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class ArxivReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArxivReaderApplication.class, args);
    }
}
