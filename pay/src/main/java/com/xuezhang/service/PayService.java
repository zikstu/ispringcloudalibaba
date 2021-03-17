package com.xuezhang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 学长
 * @date: 2021/3/16 23:23
 */
@Service
public class PayService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(){
        this.jdbcTemplate.update("insert into pays (name) values ('张三')");
    }
}
