package com.seonghyeon.migration.controller;

import com.seonghyeon.migration.service.MongoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private MongoService mongoService;

    @GetMapping("/")
    public String home() {
        return "Welcome to Home!";
    }

    @GetMapping("/test")
    public String testData() {
        return mongoService.getTestData();
    }
}
