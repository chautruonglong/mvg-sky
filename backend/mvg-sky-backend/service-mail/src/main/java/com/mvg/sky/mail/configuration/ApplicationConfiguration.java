package com.mvg.sky.mail.configuration;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan("com.mvg.sky")
public class ApplicationConfiguration implements WebMvcConfigurer {
    private final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};

    @Value("${com.mvg.sky.service-mail.external-resource}")
    private String externalResources;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        List<String> resources = new ArrayList<>(List.of(CLASSPATH_RESOURCE_LOCATIONS));
        resources.add(externalResources);

        registry.addResourceHandler("/**")
            .addResourceLocations(resources.toArray(String[]::new));
    }

    @Bean
    public CommonsRequestLoggingFilter commonsRequestLoggingFilter() {
        CommonsRequestLoggingFilter commonsRequestLoggingFilter = new CommonsRequestLoggingFilter();
        commonsRequestLoggingFilter.setIncludeClientInfo(true);
        commonsRequestLoggingFilter.setIncludeQueryString(true);
        commonsRequestLoggingFilter.setIncludeHeaders(false);
        return commonsRequestLoggingFilter;
    }
}
