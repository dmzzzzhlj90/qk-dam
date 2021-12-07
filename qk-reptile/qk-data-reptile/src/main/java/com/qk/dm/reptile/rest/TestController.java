package com.qk.dm.reptile.rest;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("")
    public DefaultCommonResult test(){
        return DefaultCommonResult.success();
    }
}
