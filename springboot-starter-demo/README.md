# starter
1.将format-spring-boot-starter项目install后将maven依赖放到pom文件中  
2.启动项目  
3.访问，可以看到访问结果  
![image](https://github.com/wtf15/MyTest/blob/master/images/49AE55B4-0BDB-4853-AEC3-5EC92A5F3C0E.png)  
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
![image](https://github.com/wtf15/MyTest/blob/master/images/5FDD2AC5-F802-4FCD-972A-EBE1A316B931.png)  
6.能够加在配置文件  
a).format-spring-boot-starter项目中增加类HelloProperties  
b).HelloAutoConfiguration类中增加注解@EnableConfigurationProperties(HelloProperties.class)  
c).springboot-starter-demo项目中在application.properties增加wtf.hello.format前缀的配置  
d).启动访问  
![image](https://github.com/wtf15/MyTest/blob/master/images/0F20AB7D-BD21-479D-AAF0-E14BE9190018.png)  
  
  
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

        
