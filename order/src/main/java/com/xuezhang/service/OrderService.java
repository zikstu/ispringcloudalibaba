package com.xuezhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 学长
 * @date: 2021/3/16 23:13
 */
@Service
public class OrderService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(){
        jdbcTemplate.update("insert into orders(name) values ('张三')");
    }
}
