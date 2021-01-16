package com.kiprenko.springfield.restcontroller.v1;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VersionController {

    private final String apiVersion;

    public VersionController(@Value("${application.options.apiVersion}") String apiVersion) {
        this.apiVersion = apiVersion;
    }

    @ApiOperation("Returns the API version.")
    @GetMapping(value = "/version")
    public String version() {
        return apiVersion;
    }
}
