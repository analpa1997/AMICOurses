package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebMvcConfig {

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
               registry.addViewController("/new").setViewName("forward:/new/index.html");
               registry.addViewController("/new/").setViewName("forward:/new/index.html");
               registry.addViewController("/new/login").setViewName("forward:/new/index.html");
               registry.addViewController("/new/signup").setViewName("forward:/new/index.html");
            }
        };
    }

}
