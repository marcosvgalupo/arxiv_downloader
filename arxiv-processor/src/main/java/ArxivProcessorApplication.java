import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.searchonmath.arxivglobal.database.repository")
@ComponentScan(basePackages = {"com.searchonmath.arxivprocessor", "com.searchonmath.arxivglobal"})
@EntityScan(basePackages = "com.searchonmath.arxivglobal.database.entity")
@EnableAsync(proxyTargetClass = true)
public class ArxivProcessorApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArxivProcessorApplication.class, args);
    }
}
