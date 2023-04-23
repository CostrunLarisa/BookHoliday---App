package com.unibuc.ro.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.List;
import java.util.Properties;
@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, getNumberFormatExceptionResolver());
        resolvers.add(1, getSimpleMappingExceptionResolver());
    }

    @Bean(name="numberFormatExceptionResolver")
    public SimpleMappingExceptionResolver getNumberFormatExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        r.setExceptionAttribute("ex");
        r.setDefaultErrorView("numberFormatException");
        Properties mappings = new Properties();
        mappings.setProperty("CustomNumberFormatException", "numberFormatException");
        r.setExceptionMappings(mappings);

        Properties statusCodes = new Properties();
        statusCodes.setProperty("CustomNumberFormatException", "400");
        r.setStatusCodes(statusCodes);

        return r;
    }

    @Bean(name="defaultMappingExceptionResolver")
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        r.setDefaultErrorView("defaultException");
        r.setExceptionAttribute("ex");

        Properties mappings = new Properties();
        mappings.setProperty("DefaultException", "DefaultException");
        r.setExceptionMappings(mappings);

        Properties statusCodes = new Properties();
        statusCodes.setProperty("DefaultException", "400");
        r.setStatusCodes(statusCodes);

        return r;
    }
}
