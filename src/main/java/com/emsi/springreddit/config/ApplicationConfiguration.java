package com.emsi.springreddit.config;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true)
public class ApplicationConfiguration {


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    Logger logger = LoggerFactory.getLogger(ApplicationConfiguration.class);

    @Bean
    public DataSource dataSource() throws JsonProcessingException {
        if (System.getenv("AWS_ACCESS_KEY_ID") == null || System.getenv("AWS_SECRET_ACCESS_KEY") == null) {
            logger.info("Missing AWS environment variables, using local datasource");
            DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
            return dataSourceBuilder
                    .driverClassName(driverClassName)
                    .url(url)
                    .username(username)
                    .password(password)
                    .build();
        }
        logger.info("AWS environment variables found, using AWS datasource");
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);

        AWSSecretsManager client =  AWSSecretsManagerClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion("eu-west-3") // replace with your AWS region
                .build();

        GetSecretValueRequest request = new GetSecretValueRequest()
                .withSecretId("usersDbPass"); // replace with your secret name

        GetSecretValueResult response = client.getSecretValue(request);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Credentials credentials = objectMapper.readValue(response.getSecretString(), Credentials.class);
        return dataSourceBuilder
                .url("jdbc:" + credentials.getEngine() + "://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDbname())
                .username(credentials.getUsername())
                .password(credentials.getPassword())
                .build();

    }

    @Getter
    @Setter
    private static class Credentials {
        private String username;
        private String password;
        private String engine;
        private String host;
        private int port;
        private String dbname;
        private String dbInstanceIdentifier;
        private String SECRET_KEY;
    }
}
