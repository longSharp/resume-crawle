package com.npp.resumec.resumepro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.npp.resumec.resumepro.dao")
public class ResumeProApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeProApplication.class, args);
    }

}
