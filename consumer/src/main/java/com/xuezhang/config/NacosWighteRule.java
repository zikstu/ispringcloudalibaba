package com.xuezhang.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/27 10:38
 */
@Slf4j
public class NacosWighteRule extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
        //读取配置文件
    }

    @Override
    public Server choose(Object o) {
        ILoadBalancer iLoadBalancer = this.getLoadBalancer();

        BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) iLoadBalancer;

        // 获取要请求的服务
        String name = baseLoadBalancer.getName();

        // 获取服务发现的相关API
        NamingService namingService = this.nacosDiscoveryProperties.namingServiceInstance();

        try {
            Instance instance = namingService.selectOneHealthyInstance(name);

            log.info("选择的实例port={},instance={}", instance.getPort(), instance);

            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }
    }
}
