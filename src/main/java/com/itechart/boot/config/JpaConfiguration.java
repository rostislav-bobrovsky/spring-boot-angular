package com.itechart.boot.config;

import com.itechart.boot.Application;
import com.itechart.boot.config.settings.SpringDatasourceSettings;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = Application.class)
@EntityScan(basePackages = "com.itechart.boot.domain")
public class JpaConfiguration {

    private final Logger LOGGER = LoggerFactory.getLogger(JpaConfiguration.class);

    private Environment env;

    @Autowired
    private SpringDatasourceSettings springDatasourceSettings;

    @Bean
    public DataSource configureDataSource() {
        HikariConfig config = new HikariConfig();

        config.setDriverClassName(springDatasourceSettings.getDriverClassName());
        config.setJdbcUrl(springDatasourceSettings.getUrl());
        config.setUsername(springDatasourceSettings.getUsername());
        config.setPassword(springDatasourceSettings.getPassword());

        return new HikariDataSource(config);
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}