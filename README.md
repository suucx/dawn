# Dawn
dawn取自黎明破晓之意
#### 简介：
**dawn为一个springCloud微服务框架，从零开始搭建；于个人学习使用！！！**

- 基于SpringBoot/SpringCloud；
- 使用nacos作为配置与服务中心；
- 使用springCloud-gateway中间件作为网关；
- 使用feign作为服务器直接调用默认ribbon为客户端负载均衡；

#### 版本及环境信息：
- idea 
- JDK8 
- Springboot-2.3.0.RELEASE 
- SpringCloud-Hoxton.SR4
- Nacos 1.2.1

#### 服务模块：
- dawn-core:基础类/常量等

- dawn-auth：认证模块

|功能|情况|
|-|-|
|集成spring-oauth2完成token下发|√|

- dawn-common:通用模块

|功能|情况|
|-|-|
|注解进行角色判定|待定|
|注解进行统一返回参数封装|√|
|通用异常处理|√|

- dawn-gateway：网关模块

|功能|情况|
|-|-|
|动态路由|√|
|JWT统一验签|√|

- dawn-jwt-spring-boot-stater:jwt工具

|功能|情况|
|-|-|
|JWT工具|√|

- dawn-mysql:引入druid MybatisPlus

#### 简易架构图：
![](https://github.com/suucx/dawn/blob/master/script/1.png)





