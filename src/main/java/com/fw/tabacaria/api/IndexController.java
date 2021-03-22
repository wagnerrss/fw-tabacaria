package com.fw.tabacaria.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String get(){
        return "Get api FW para Tabacaria";
    }
    @PostMapping
    public String post(){
        return "Post api FW para Tabacaria";
    }
    @PutMapping
    public String put(){
        return "Put api FW para Tabacaria";
    }
    @DeleteMapping
    public String delete(){
        return "Delete api FW para Tabacaria";
    }
}
