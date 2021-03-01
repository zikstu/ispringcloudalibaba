package com.xuezhang.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/28 21:27
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();

        registrationBean.setFilter(new CommonFilter());

        registrationBean.addUrlPatterns("/*");

        registrationBean.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY, "false");

        registrationBean.setName("sentinelFilter");

        return registrationBean;
    }
}
