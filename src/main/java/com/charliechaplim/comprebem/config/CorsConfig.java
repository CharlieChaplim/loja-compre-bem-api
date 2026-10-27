package com.charliechaplim.comprebem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Origens locais do Expo Web durante o desenvolvimento.
                // Restringir para os dominios oficiais antes de publicar em producao.
                registry.addMapping("/api/**")
                        .allowedOriginPatterns(
                                "http://localhost:*", "http://127.0.0.1:*",
                                "http://192.168.*:*", "http://10.*:*", "http://172.*:*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}
