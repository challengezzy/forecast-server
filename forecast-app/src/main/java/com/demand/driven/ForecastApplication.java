package com.demand.driven;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.demand.driven.mapper")
@SpringBootApplication
public class ForecastApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForecastApplication.class, args);
    }

}
