package com.qinzi123;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @title: QinziApplication
 * @package: com.qinzi123
 * @projectName: trunk
 * @description: 亲子云商
 * @author: jie.yuan
 * @date: 2019/11/27 0025 15:09
 * 注意:该代码仅为中企云服科技传阅，严禁其他商业用途！
 */
@SpringBootApplication
@EnableScheduling
public class QinziApplication {
    public static void main(String[] args) {
        SpringApplication.run(QinziApplication.class, args);
    }
}
