# ispringcloudalibaba

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

  
