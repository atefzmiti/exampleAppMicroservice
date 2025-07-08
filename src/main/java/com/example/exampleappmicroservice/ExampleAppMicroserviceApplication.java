package com.example.exampleappmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {SecurityFilterAutoConfiguration.class})
@AutoConfiguration(after = { DataSourceAutoConfiguration.class })
@EnableConfigurationProperties(JpaProperties.class)
@EnableFeignClients
@EnableEurekaClient
@EnableJpaRepositories(basePackages = "com.example.exampleappmicroservice.DAO")
@EnableElasticsearchRepositories(basePackages = "com.example.exampleappmicroservice.DAO.elasticsearch")
public class ExampleAppMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleAppMicroserviceApplication.class, args);

	}

}
