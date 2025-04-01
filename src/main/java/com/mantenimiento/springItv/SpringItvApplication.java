package com.mantenimiento.springItv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.mantenimiento.springItv.services", 
		"com.mantenimiento.springItv.repositories", 
		"com.mantenimiento.springItv.controller"})
@EntityScan("com.mantenimiento.springItv.entities")
public class SpringItvApplication extends SpringBootServletInitializer{
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringItvApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(SpringItvApplication.class, args);
	}

}
