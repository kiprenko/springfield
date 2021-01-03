package com.kiprenko.springfield.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionController {

    private static final String VERSION = "0.1";

    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }
}
