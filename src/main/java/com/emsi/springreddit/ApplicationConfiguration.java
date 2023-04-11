package com.emsi.springreddit;


import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Base64;

import lombok.Getter;
import lombok.Setter;

@Configuration
@PropertySource(value = { "classpath:application.properties" }, ignoreResourceNotFound = true)
public class ApplicationConfiguration {

    public ApplicationConfiguration() throws JsonProcessingException {
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                .withRegion("eu-west-3") // replace with your AWS region
                .build();

        GetSecretValueRequest request = new GetSecretValueRequest()
                .withSecretId("usersDbPass"); // replace with your secret name

        GetSecretValueResult response = client.getSecretValue(request);

        if (response.getSecretString() != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            Credentials credentials = objectMapper.readValue(response.getSecretString(), Credentials.class);

            System.setProperty("spring.datasource.driver-class-name", "com.mysql.jdbc.Driver");
            System.setProperty("spring.datasource.url", "jdbc:" + credentials.getEngine() + "://" + credentials.getHost() + ":" + credentials.getPort() + "/" + credentials.getDbname());
            System.setProperty("spring.datasource.username", credentials.getUsername());
            System.setProperty("spring.datasource.password", credentials.getPassword());
        } else {
            byte[] binarySecretData = Base64.getDecoder().decode(response.getSecretBinary().array());
            // process the binarySecretData
        }
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
    }
}
