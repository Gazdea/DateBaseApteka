package springConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("java")
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableJpaRepositories("repositories")
public class SpringConfig implements WebMvcConfigurer {

}
