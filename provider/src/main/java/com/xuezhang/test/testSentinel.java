package com.xuezhang.test;

import org.springframework.web.client.RestTemplate;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/28 20:58
 */
public class testSentinel {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        for (int i = 0; i < 1000; i++) {
            String s = restTemplate.getForObject("http://localhost:8201/provider/list", String.class);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
