package com.musinsa.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
//@ComponentScan(basePackages = {"com.musinsa.product.mapper"})
@EntityScan("com.musinsa.product.model.entity")
@SpringBootApplication(scanBasePackages = "com.musinsa")
public class ProductApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductApiApplication.class, args);
	}

}
