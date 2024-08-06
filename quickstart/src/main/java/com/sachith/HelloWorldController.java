package com.sachith;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloWorldController {

    @GetMapping(path = "/hello")
    public String helloWorld() {
        return "Sachith";
    }
}
