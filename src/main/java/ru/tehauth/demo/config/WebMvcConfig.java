package ru.tehauth.demo.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;
import java.io.IOException;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File fileUploadPath = new File(uploadPath);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdir();
        }
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:///" + fileUploadPath.getAbsolutePath() + "/");
        System.out.println("file:///" + fileUploadPath.getAbsolutePath() + "/");

    }

}

