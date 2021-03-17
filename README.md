## 1.什么是Spring Cloud Alibaba

#### 1.1 什么Spring cloud

是一系列分布式框架的集合，基于Spring Boot 进行开发的。是一种规范，将不同公司生产的不同组件进去集成，以Spring Boot 的风格进行集成，开发者就不需要关注底层的整合实现，而是开箱即用的，需要哪个组件就用Spring Boot 整合进来。

#### 1.2 什么是Spring Cloud Alibaba 

Spring Cloud Netflix

Spring Cloud Alibaba

工程结构：Spring Boot ---> Spring Cloud ---> Spring Cloud Alibaba

#### 1.3 创建maven工程，pom.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.0.RELEASE</version>
        <relativePath/>
    </parent>

    <groupId>com.xuezhang</groupId>
    <artifactId>ispringcloudalibaba</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR3</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.2.1.RELEASE</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

## 2. 服务治理 

#### 服务注册 + 服务发现 (Nacos)

下载并安装Nacos `https://nacos.io/zh-cn/docs/quick-start.html`

1. ##### 服务注册

   - 创建子工程provider, pom.xml

   ```java
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
       <parent>
           <groupId>com.xuezhang</groupId>
           <artifactId>ispringcloudalibaba</artifactId>
           <version>0.0.1-SNAPSHOT</version>
       </parent>
   
       <groupId>com.xuezhang</groupId>
       <artifactId>provider</artifactId>
       <version>0.0.1-SNAPSHOT</version>
       <name>provider</name>
   
       <properties>
           <java.version>1.8</java.version>
       </properties>
   
       <dependencies>
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter</artifactId>
           </dependency>
   
           <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-test</artifactId>
               <scope>test</scope>
           </dependency>
   
           <dependency>
               <groupId>com.alibaba.cloud</groupId>
               <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
               <version>2.2.1.RELEASE</version>
               <exclusions>
                   <exclusion>
                       <groupId>com.netflix.servo</groupId>
                       <artifactId>servo-internal</artifactId>
                   </exclusion>
               </exclusions>
           </dependency>
   
       </dependencies>
   
       <build>
           <plugins>
               <plugin>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-maven-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   
   </project>
   ```

- 创建配置文件application.yml

```yaml
server:
  port: 8201

spring:
  application:
    name: provider
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848/
```

- 启动三个provider 实例
- 创建子工程consumer, pom.xml

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>com.xuezhang</groupId>
        <artifactId>ispringcloudalibaba</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <groupId>com.xuezhang</groupId>
    <artifactId>consumer</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>consumer</name>
    <properties>
        <java.version>1.8</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
            <version>2.2.1.RELEASE</version>
            <exclusions>
                <exclusion>
                    <groupId>com.netflix.servo</groupId>
                    <artifactId>servo-internal</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

- 创建配置文件，application.yml

```yaml
server:
  port: 8204
spring:
  application:
    name: consumer
  cloud:
    nacos:
      discovery:
       server-addr: localhost:8848
```

- 创建控制器

```java
package com.xuezhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/26 22:25
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    private DiscoveryClient discoveryClient;

    public ConsumerController(DiscoveryClient discoveryClient){
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/instances")
    public List<ServiceInstance> instances(){
        List<ServiceInstance> instances = this.discoveryClient.getInstances("provider");

        return instances;
    }
}

```

- 测试 `http://localhost:8204/consumer/instances`

##### 服务调用

- provider服务下创建控制器

```java
package com.xuezhang.controller;

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
    @Value("${server.port}")
    private String port;

    @GetMapping("/index")
    public String index(){
        return port;
    }
}
```

- consumer服务创建RestTemplate配置类

```java
package com.xuezhang.config;

import lombok.extern.slf4j.Slf4j;
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
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();

        //解决中文乱码
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        log.info("RestTemplate注入成功！");

        return restTemplate;
    }
}
```

- 修改consumer服务的控制器

```java
package com.xuezhang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @description:
 * @author: 学长
 * @date: 2021/2/26 22:25
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {
    private DiscoveryClient discoveryClient;
    private RestTemplate restTemplate;

    public ConsumerController(DiscoveryClient discoveryClient, RestTemplate restTemplate){
        this.discoveryClient = discoveryClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/instances")
    public List<ServiceInstance> instances(){
        List<ServiceInstance> instances = this.discoveryClient.getInstances("provider");

        return instances;
    }

    @GetMapping("/index")
    public String index(){
        List<ServiceInstance> list = this.discoveryClient.getInstances("provider");

        int index = ThreadLocalRandom.current().nextInt(list.size());

        ServiceInstance serviceInstance = list.get(index);

        String url = serviceInstance.getUri() + "/provider/index";

        String s = this.restTemplate.getForObject(url, String.class);

        return "当前调用端口为 " + serviceInstance.getPort() + "的服务，返回结果为 " + s;
    }
}
```

- 重启服务，测试 `http://localhost:8204/consumer/index`

## 3.Ribbon

Ribbon 不是Spring Cloud Alibaba 的组件，是Netflix 提供的，默认使用轮询算法（依次调用）

#### 负载均衡实现

- 修改consumer 服务的 RestTempleteConfig 配置类

```java
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
```

- ConsumerController 添加测试方法

```java
@GetMapping("/index")
public String index(){
    return restTemplate.getForObject("http://provider/provider/index", String.class);
}
```

- 测试 `http://localhost:8204/consumer/index`

#### 基于随机的算法

修改consumer服务的配置文件

```yaml
server:
  port: 8204
spring:
  application:
    name: consumer
  cloud:
    nacos:
      discovery:
       server-addr: localhost:8848

provider:
  ribbon:
    NFLaodBalancerRuleClassName: com.netflix.loadbalancer.RandomRule
```

#### 基于权重的算法

- 创建权重配置类

## 4.Sentinel 服务限流降级

安装启动 Sentinel

- pom.xml 引入相关依赖

  ```xml
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
      <version>2.2.1.RELEASE</version>
  </dependency>
  
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
      <version>2.3.1.RELEASE</version>
  </dependency>
  ```

- 修改配置文件

  ```yaml
  server:
    port: 8201
  spring:
    application:
      name: provider
    cloud:
      nacos:
        discovery:
          server-addr: localhost:8848
      sentinel:
         transport:
           dashboard: localhost:8858
         filter:
           enabled: false
  management:
    endpoints:
      web:
        exposure:
          include: '*'
  ```

## 5.服务网关（gateway）

- 创建子工程

- 添加依赖

  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-gateway</artifactId>
      <version>2.2.5.RELEASE</version>
  </dependency>
  ```

- 创建配置文件

  ```yaml
  server:
    port: 8181
  spring:
    application:
      name: gateway
    cloud:
      gateway:
        discovery:
          locator:
            enabled: true
        routes:
          - id: provider_route
            uri: http://localhost:8201
            predicates:
              - Path=/provider/**
            filters:
              - StripPrefix=1
  ```

- 配置限流

  ```java
  package com.xuezhang.config;
  
  import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
  import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
  import org.springframework.beans.factory.ObjectProvider;
  import org.springframework.cloud.gateway.filter.GlobalFilter;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.core.Ordered;
  import org.springframework.core.annotation.Order;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.MediaType;
  import org.springframework.http.codec.ServerCodecConfigurer;
  import org.springframework.web.reactive.function.BodyInserters;
  import org.springframework.web.reactive.function.server.ServerResponse;
  import org.springframework.web.reactive.result.view.ViewResolver;
  import org.springframework.web.server.ServerWebExchange;
  import reactor.core.publisher.Mono;
  
  import javax.annotation.PostConstruct;
  import java.util.*;
  
  /**
   * @description:
   * @author: 学长
   * @date: 2021/3/16 21:50
   */
  @Configuration
  public class GatewayConfiguration {
      private final List<ViewResolver> viewResolvers;
      private final ServerCodecConfigurer serverCodecConfigurer;
  
      public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer) {
          this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
          this.serverCodecConfigurer = serverCodecConfigurer;
      }
  
      //配置限流的异常处理
      @Bean
      @Order(Ordered.HIGHEST_PRECEDENCE)
      public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
          return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
      }
  
      //配置初始化的限流参数
      @PostConstruct
      public void initGatewayRules(){
          Set<GatewayFlowRule> rules = new HashSet<>();
          rules.add(new GatewayFlowRule("provider_route")
                  .setCount(1)
                  .setIntervalSec(1) );
          GatewayRuleManager.loadRules(rules);
      }
  
      @Bean
      @Order(Ordered.HIGHEST_PRECEDENCE)
      public GlobalFilter sentinelGatewayFilter() {
          return new SentinelGatewayFilter();
      }
  
      //自定义限流异常页面
      @PostConstruct public void initBlockHandlers(){
          BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
              @Override
              public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                  Map map = new HashMap();
                  map.put("code",0);
                  map.put("msg","被限流了");
                  return ServerResponse
                      .status(HttpStatus.OK)
                      .contentType(MediaType.APPLICATION_JSON)
                      .body(BodyInserters.fromObject(map));
              }
          };
          GatewayCallbackManager.setBlockHandler(blockRequestHandler);
      }
  }
  ```

  

### 基于nacos实现网关

- pom.xml添加nacos依赖

  ```xml
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
      <version>2.2.1.RELEASE</version>
  </dependency>
  ```

  

- 修改配置文件application.yml

  ```yaml
  server:
    port: 8181
  spring:
    application:
      name: gateway
    cloud:
      gateway:
        discovery:
          locator:
            enabled: true
  #      routes:
  #        - id: provider_route
  #          uri: http://localhost:8201
  #          predicates:
  #            - Path=/provider/**
  #          filters:
  #            - StripPrefix=1
  ```

  

### 基于API分组限流

- 修改配置文件

  ```java
  package com.xuezhang.config;
  
  import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
  import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiDefinition;
  import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPathPredicateItem;
  import com.alibaba.csp.sentinel.adapter.gateway.common.api.ApiPredicateItem;
  import com.alibaba.csp.sentinel.adapter.gateway.common.api.GatewayApiDefinitionManager;
  import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
  import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
  import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
  import org.springframework.beans.factory.ObjectProvider;
  import org.springframework.cloud.gateway.filter.GlobalFilter;
  import org.springframework.context.annotation.Bean;
  import org.springframework.context.annotation.Configuration;
  import org.springframework.core.Ordered;
  import org.springframework.core.annotation.Order;
  import org.springframework.http.HttpStatus;
  import org.springframework.http.MediaType;
  import org.springframework.http.codec.ServerCodecConfigurer;
  import org.springframework.web.reactive.function.BodyInserters;
  import org.springframework.web.reactive.function.server.ServerResponse;
  import org.springframework.web.reactive.result.view.ViewResolver;
  import org.springframework.web.server.ServerWebExchange;
  import reactor.core.publisher.Mono;
  
  import javax.annotation.PostConstruct;
  import java.util.*;
  
  /**
   * @description:
   * @author: 学长
   * @date: 2021/3/16 21:50
   */
  @Configuration
  public class GatewayConfiguration {
      private final List<ViewResolver> viewResolvers;
      private final ServerCodecConfigurer serverCodecConfigurer;
  
      public GatewayConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider, ServerCodecConfigurer serverCodecConfigurer) {
          this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
          this.serverCodecConfigurer = serverCodecConfigurer;
      }
  
      //配置限流的异常处理
      @Bean
      @Order(Ordered.HIGHEST_PRECEDENCE)
      public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
          return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
      }
  
      //配置初始化的限流参数
      @PostConstruct
      public void initGatewayRules(){
          Set<GatewayFlowRule> rules = new HashSet<>();
          rules.add(new GatewayFlowRule("provider_api1")
                  .setCount(1)
                  .setIntervalSec(1) );
          rules.add(new GatewayFlowRule("provider_api2")
                  .setCount(1)
                  .setIntervalSec(1) );
          GatewayRuleManager.loadRules(rules);
      }
  
      @Bean
      @Order(Ordered.HIGHEST_PRECEDENCE)
      public GlobalFilter sentinelGatewayFilter() {
          return new SentinelGatewayFilter();
      }
  
      //自定义限流异常页面
      @PostConstruct public void initBlockHandlers(){
          BlockRequestHandler blockRequestHandler = new BlockRequestHandler() {
              @Override
              public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                  Map map = new HashMap();
                  map.put("code",0);
                  map.put("msg","被限流了");
                  return ServerResponse
                          .status(HttpStatus.OK)
                          .contentType(MediaType.APPLICATION_JSON)
                          .body(BodyInserters.fromObject(map));
              }
          };
          GatewayCallbackManager.setBlockHandler(blockRequestHandler);
      }
  
      //自定义API分组
      @PostConstruct private void initCustomizedApis(){
          Set<ApiDefinition> definitions = new HashSet<>();
          ApiDefinition api1 = new ApiDefinition("provider_api1").
                  setPredicateItems(new HashSet<ApiPredicateItem>(){{
                      add(new ApiPathPredicateItem()
                              .setPattern("/provider/provider/api1/**")
                              .setMatchStrategy(SentinelGatewayConstants.URL_MATCH_STRATEGY_PREFIX));
                  }});
          ApiDefinition api2 = new ApiDefinition("provider_api2").
                  setPredicateItems(new HashSet<ApiPredicateItem>(){{
                      add(new ApiPathPredicateItem()
                              .setPattern("/provider/provider/api2/demo1"));
                  }});
          definitions.add(api1);
          definitions.add(api2);
  
          GatewayApiDefinitionManager.loadApiDefinitions (definitions);
      }
  }
  ```

- 增加测试api

  ```java
  @GetMapping("/api1/demo1")
  public String demo1(){ return "api1-demo1"; }
  
  @GetMapping("/api1/demo2")
  public String demo2(){ return "api1-demo2"; }
  
  @GetMapping("/api2/demo1")
  public String demo3(){ return "api2-demo1"; }
  
  @GetMapping("/api2/demo2")
  public String demo4(){ return "api2-demo2"; }
  ```

  

## 6.分布式事务（Seata）

### 配置seata-server

- 下载安装 seata-server

- 修改配置文件 registry.conf

  ```conf
  registry {
    # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
    type = "nacos"
  
    nacos {
      serverAddr = "localhost"
      namespace = "public"
      cluster = "default"
    }
  }
  
  config {
    # file、nacos 、apollo、zk、consul、etcd3
    type = "nacos"
  
    nacos {
      serverAddr = "localhost"
      namespace = "public"
      cluster = "default"
    }
  }
  ```

- 修改文件 nacos-config.txt

  ![image-20210317194412476](/Users/xuezhang/Library/Application Support/typora-user-images/image-20210317194412476.png)

- 启动文件 nacos-config.sh

### 配置工程

- Pom.xml

  ```xml
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
      <version>2.1.1.RELEASE</version>
  </dependency>
  
  <dependency>
      <groupId>com.alibaba.cloud</groupId>
      <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
      <version>2.2.1.RELEASE</version>
  </dependency>
  ```

- 创建配置文件 bootstrap.yml

  ```yaml
  spring:
    application:
      name: order
    cloud:
      nacos:
        config:
          server-addr: localhost:8848
          namespace: public
          group: SEATA_GROUP
      alibaba:
        seata:
          tx-service-group: ${spring.application.name}
  ```

