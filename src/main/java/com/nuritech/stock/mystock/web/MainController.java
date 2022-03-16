package com.nuritech.stock.mystock.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    @GetMapping("/index")
    public String main() {
        return "index.html";
    }
}
