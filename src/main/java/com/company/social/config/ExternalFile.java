package com.company.social.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ExternalFile   implements WebMvcConfigurer{
    String ExternalFile = "C:/Users/Legend/Desktop/social/gallary/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/gallery/**").addResourceLocations(ExternalFile);
    }
    
}
