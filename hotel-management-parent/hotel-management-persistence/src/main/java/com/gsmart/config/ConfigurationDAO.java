package com.gsmart.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Lazy
@Configuration
@EnableJpaRepositories(basePackages = "com.gsmart.repository")
@ComponentScan
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {ApplicationDataSource.class})
@EntityScan(basePackages = "com.gsmart.model")
public class ConfigurationDAO {
	
}
