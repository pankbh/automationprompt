package com.automationprompt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/history")
    public String history() {
        return "history";
    }

    @GetMapping("/stats")
    public String stats() {
        return "stats";
    }
}
