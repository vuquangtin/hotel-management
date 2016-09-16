package com.gsmart.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.gsmart")
@ComponentScan(basePackages = "com.gsmart")
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {ApplicationDataSource.class})
@EntityScan(basePackages = "com.gsmart")
public class ConfigurationDAO {
	
}
