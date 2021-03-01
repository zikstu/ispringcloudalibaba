package com.xuezhang.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/28 21:15
 */
@Service
public class ProviderService {
    @SentinelResource
    public void test(){
        System.out.println("test");
    }
}
