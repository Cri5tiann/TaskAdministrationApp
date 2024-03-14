package com.example.demo.config;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom-configuration")
@Data
@Getter
@Setter
public class CustomConfigurationYAMLConfig {

    private String property1;
    private String property2;
}
