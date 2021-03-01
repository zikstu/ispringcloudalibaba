package com.xuezhang.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/26 23:16
 */
@Configuration
@Slf4j
public class RestTemplateConfig {
    /**
     * 配置RestTemplate
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        //解决中文乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        log.info("RestTemplate注入成功！");

        return restTemplate;
    }
}
