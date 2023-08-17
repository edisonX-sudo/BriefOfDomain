package org.xsk.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(Application.BASE_PACKAGE)
@EnableJpaRepositories(basePackages = Application.DB_PACKAGE)
@EntityScan(basePackages = Application.DB_PACKAGE)
public class Application {
    public static final String BASE_PACKAGE = "org.xsk";
    public static final String DB_PACKAGE = "org.xsk.infra.db";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
