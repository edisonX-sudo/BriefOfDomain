package org.xsk.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(Application.BASE_PACKAGE)
public class Application {
    public static final String BASE_PACKAGE = "org.xsk";
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
