package com.kiprenko.springfield.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class VersionController {

    private static final String VERSION = "1.0";

    @GetMapping(value = "/version")
    public String version() {
        return VERSION;
    }
}
