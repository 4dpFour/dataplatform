## 数据库地址

数据库IP：118.178.95.33

端口：3306

数据库名：dataplatform

用户名：root

密码：123456

## 配置方法

配置文件：/src/main/resources/application.properties

```properties
#port 项目启动端口
server.port=8080
#mybatis
#数据库连接字符串
spring.datasource.url=jdbc:mysql://118.178.95.33:3306/dataplatform
#数据库用户名
spring.datasource.username=root
#数据库密码
spring.datasource.password=123456
#数据库配置文件地址
mybatis.config-location=classpath:mybatis-config.xml
```

