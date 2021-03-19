package com.xuezhang.controller;

import com.xuezhang.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: 学长
 * @date: 2021/3/16 23:13
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @GetMapping("/save")
    @GlobalTransactional
    public String save(){
        //订单
        this.orderService.save();

        //自定义异常
        int i = 10/0;

        //支付
        String s = this.restTemplate.getForObject("http://localhost:8206/pay/save", String.class);

        System.out.println("支付接口：" + s);

        return "success";
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World";
    }
}
