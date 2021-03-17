package com.xuezhang.controller;

import com.xuezhang.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/26 23:01
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
    private ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @Value("${server.port}")
    private String port;

    @GetMapping("/index")
    public String index(){
        return port;
    }

    @GetMapping("/list")
    public String list(){
        return "list";
    }

    @GetMapping("/test1")
    public String test1(){
        this.providerService.test();
        return "test1";
    }

    @GetMapping("/test2")
    public String test2(){
        this.providerService.test();
        return "test2";
    }

    @GetMapping("/api1/demo1")
    public String demo1(){ return "api1-demo1"; }

    @GetMapping("/api1/demo2")
    public String demo2(){ return "api1-demo2"; }

    @GetMapping("/api2/demo1")
    public String demo3(){ return "api2-demo1"; }

    @GetMapping("/api2/demo2")
    public String demo4(){ return "api2-demo2"; }
}
