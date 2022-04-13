package com.ssafy.chaintract;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChaintractApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChaintractApplication.class, args);
	}


}
