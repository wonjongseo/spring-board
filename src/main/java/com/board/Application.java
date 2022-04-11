package com.board;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 해당 패키지부터 설정을 읽어간다.
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // .run 으로 인해 내장 was 를 실행함
        SpringApplication.run(Application.class, args);
    }
}
