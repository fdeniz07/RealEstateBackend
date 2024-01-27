package com.prettier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //(exclude = SecurityAutoConfiguration.class) // exclude ile Spring Security nin default dogrulama davranisini devre disi birakiyoruz
//@ComponentScan(basePackages = "com.prettier") // SecurityConfig sınıfının bulunduğu paketi belirtin
public class RealEstateApplication  {

    public static void main(String[] args) {

        SpringApplication.run(RealEstateApplication.class, args);
    }
}


