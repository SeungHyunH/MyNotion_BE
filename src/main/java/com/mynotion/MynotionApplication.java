package com.mynotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class MynotionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MynotionApplication.class, args);
	}

}
