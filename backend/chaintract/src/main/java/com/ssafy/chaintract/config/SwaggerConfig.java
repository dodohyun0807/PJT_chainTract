package com.ssafy.chaintract.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Server;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api(){
        Server serverLocal = new Server("local", "http://localhost:8080", "for local usages", Collections.emptyList(), Collections.emptyList());
        Server testServer = new Server("test", "https://j6c105.p.ssafy.io", "for testing", Collections.emptyList(), Collections.emptyList());

        return new Docket(DocumentationType.OAS_30).servers(testServer)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ssafy.chaintract.api"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }
}
