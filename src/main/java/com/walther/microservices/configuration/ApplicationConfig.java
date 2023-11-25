package com.walther.microservices.configuration;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
@ToString
public class ApplicationConfig {
    private String name;
    private String year;
    private String edition;
    private String[] countries;

    /*@Value("${java_home}")
    private String java;*/
}
