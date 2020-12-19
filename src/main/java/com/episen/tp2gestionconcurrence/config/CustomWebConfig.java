package com.episen.tp2gestionconcurrence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class CustomWebConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageableResolver =new PageableHandlerMethodArgumentResolver();
        pageableResolver.setPageParameterName("page");
        pageableResolver.setSizeParameterName("pageSize");
        resolvers.add(pageableResolver);
    }
}
