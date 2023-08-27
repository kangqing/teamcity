package com.kangqing.teamcity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@RequestMapping
public class TeamcityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamcityApplication.class, args);
    }

    @GetMapping("/test")
    public String test() {
        return "Hello teamcity!";
    }

}
