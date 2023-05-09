package com.smart.agriculture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jhc.mapper")
public class Controller {
    public static void main(String[] args) {
        SpringApplication.run(Controller.class, args);
    }

}
