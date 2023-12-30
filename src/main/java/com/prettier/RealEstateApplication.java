package com.prettier;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RealEstateApplication implements CommandLineRunner { //CommandLineRunner ile uygulama basladiginda calismasini istedigimiz kodlari calistir

	//Model Mapper - 1. Asama : Pom dosyasina ilgili dependency eklenir.
	//Model Mapper - 2. Asama : Main class'ina constructor Dependency Injection olarak gecilir.
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(RealEstateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}

