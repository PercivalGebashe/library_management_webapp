package com.github.percivalgebashe.assignment_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableMethodSecurity
@RestController
@RequestMapping("/api/v1/library")
public class LibraryRestController {

    @GetMapping("admin/hello")
    public String hello() {
        return "Hello World!";
    }

}
