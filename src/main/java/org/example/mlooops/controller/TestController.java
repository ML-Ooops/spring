package org.example.mlooops.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class TestController {

    @GetMapping("/test")
    public String index() {
        return "test controller";
    }

    @GetMapping("/")
    public String mainP() {
        return "main controller";
    }

    @GetMapping("/sample")
    public String sampleApi() {
        return "Hello, Spring REST Docs!";
    }
}
