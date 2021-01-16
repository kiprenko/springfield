package com.kiprenko.springfield.configuration.swagger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@NoArgsConstructor
@Getter
@Setter
@Validated
@ConfigurationProperties("application.swagger")
public class SwaggerProperties {
    private String title;
    private String description;
    private String contactEmail;
    private String contactName;
    private String contactUrl;
    private String license;
}
