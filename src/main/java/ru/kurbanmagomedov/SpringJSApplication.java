package ru.kurbanmagomedov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class SpringJSApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJSApplication.class, args);
	}

	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		return new HiddenHttpMethodFilter();
	}

}
