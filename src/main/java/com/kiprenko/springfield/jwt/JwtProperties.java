package com.kiprenko.springfield.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@Getter
@Setter
@Validated
@ConfigurationProperties("application.jwt")
public class JwtProperties {

    @NotBlank
    private String passphrase;
    private String tokenPrefix;
    @NotNull
    @Positive
    private Integer tokenExpirationHours;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
