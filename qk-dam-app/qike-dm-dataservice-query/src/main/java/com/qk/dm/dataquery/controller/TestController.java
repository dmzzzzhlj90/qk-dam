package com.qk.dm.dataquery.controller;

import com.qk.dm.dataquery.event.DatasourceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {
    @Autowired
    ApplicationEventPublisher publisher;


    @RequestMapping("/tt")
    public void c(){
    }
}
