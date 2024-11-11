package com.gjh.batchsqltool;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.gjh.batchsqltool.mapper")
public class BatchSqlToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchSqlToolApplication.class, args);
    }

}
