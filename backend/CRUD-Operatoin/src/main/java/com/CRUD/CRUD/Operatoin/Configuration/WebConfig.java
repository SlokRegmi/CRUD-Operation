package com.CRUD.CRUD.Operatoin.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // Allow all endpoints
                .allowedOrigins("*")  // Allow requests from all origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH") // Allow these HTTP methods
                .allowedHeaders("*")  // Allow all headers
                .allowCredentials(false); // Change to true if credentials (cookies/auth) are required
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")  // Map URL path `/uploads/**`
                .addResourceLocations("file:///C:/uploads/");  // Serve static files from `C:/uploads/`
    }
}
