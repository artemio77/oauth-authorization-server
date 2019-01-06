package com.derevets.artem.config;


import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author Artem Derevets
 */
@Configuration
@EnableJpaRepositories(basePackages = {
        "com.derevets.artem"
})
@EnableTransactionManagement
@Slf4j
public class PersistenceConfig {

}





