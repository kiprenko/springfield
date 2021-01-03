package com.kiprenko.springfield.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("/base.properties")
@ComponentScan("com.kiprenko.springfield")
public class BaseConfiguration {
}
