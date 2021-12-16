package com.nanqiu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.nanqiu.mapper")
public class BathCenterApplication {

    public static void main(String[] args){
        SpringApplication.run(BathCenterApplication.class,args);
    }

}
