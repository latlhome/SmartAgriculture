package com.smart.agriculture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.smart.agriculture.mapper")
@EnableSwagger2
public class Controller {
    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }

}
