package com.example.work.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@ConfigurationProperties(prefix = "security.web")
public class WebConfigurer implements WebMvcConfigurer {

    @Setter
    private List<String> excludes;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
    }
}
