package com.qk.dm.dataservice.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @RequestMapping("/rq")
    public String rp(){
        return "hello world";
    }
}
