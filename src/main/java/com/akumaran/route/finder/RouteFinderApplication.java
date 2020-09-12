package com.akumaran.route.finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class RouteFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteFinderApplication.class, args);
	}

}