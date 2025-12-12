package com.his;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 医院信息系统主启动类
 */
@SpringBootApplication
@MapperScan("com.his.mapper") // 扫描Mapper接口
public class HisSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HisSystemApplication.class, args);
    }

}