package org.base.mieuf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
/**
 * 配置扫描位置，一般的不会成功要在pom的build中添加配置
 */
@MapperScan("org.base.mieuf.project.bussiness")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

