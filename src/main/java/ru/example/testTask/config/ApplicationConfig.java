package ru.example.testTask.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.reactive.HiddenHttpMethodFilter;
import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class ApplicationConfig  implements WebMvcConfigurer {

    private final Environment environment;

    ApplicationConfig(Environment environment) {
        this.environment = environment;
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/welcomePage.html");
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(Objects.requireNonNull(environment.getProperty("spring.datasource.url")));
        dataSource.setUsername(Objects.requireNonNull(environment.getProperty("spring.datasource.username")));
        dataSource.setPassword(Objects.requireNonNull(environment.getProperty("spring.datasource.password")));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}
