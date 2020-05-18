# starter
1.将format-spring-boot-starter项目install后将maven依赖放到pom文件中  
2.启动项目  
3.访问，可以看到访问结果  
![image](https://user-images.githubusercontent.com/30641856/79949599-5eea3180-84a8-11ea-937b-d8c11aac2210.png)  
4.在pom添加fastjson的依赖  
```
<dependency>  
  <groupId>com.alibaba</groupId>  
  <artifactId>fastjson</artifactId>  
  <version>1.2.56</version>  
  <optional>true</optional>  
</dependency>
```
5.访问，可以看到访问结果  
![image](https://user-images.githubusercontent.com/30641856/79949486-37936480-84a8-11ea-9ec8-c1b763cdc979.png)  
6.能够加在配置文件  
a).format-spring-boot-starter项目中增加类HelloProperties  
b).HelloAutoConfiguration类中增加注解@EnableConfigurationProperties(HelloProperties.class)  
c).springboot-starter-demo项目中在application.properties增加wtf.hello.format前缀的配置  
d).启动访问  
![image](https://user-images.githubusercontent.com/30641856/79949162-af14c400-84a7-11ea-99a9-223d568fa28f.png)  
  
  
# jdbc多数据源  
1.配置pom文件  
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.16</version>
</dependency>
```
2.配置多数据源JdbcDataSourceConfig  
3.配置文件application.properties  
4.测试文件JdbcTests  
  
  
# actuator
1. 增加依赖  
```
<dependency> 
	<groupId>org.springframework.boot</groupId> 
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
2. 启动项目，访问http://localhost:8080/actuator  
![image](https://user-images.githubusercontent.com/30641856/79949748-9c4ebf00-84a8-11ea-9dad-c610ab0ad5b0.png)  
3. 增加下面配置后的展示  
```
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```
![image](https://user-images.githubusercontent.com/30641856/79949407-1af72c80-84a8-11ea-9183-ecbdebe5f70b.png)  
![image](https://user-images.githubusercontent.com/30641856/79949809-b38dac80-84a8-11ea-8de5-9086bcdf6ff9.png)  

        
# JMX(Java 管理扩展)
1. 启动JMXMain  
2. 打开jconsole  
![image](https://user-images.githubusercontent.com/30641856/79949693-8214e100-84a8-11ea-9374-66274c47c95f.png)  
