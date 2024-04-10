package pl.project.infrastructure.configuration;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.project.api.controller.addresses.ResourcePaths;

import java.util.List;

@Configuration
public class WebConfiguration implements WebMvcConfigurer
//        extends WebMvcConfigurationSupport
{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/dish_photos/**")
                .addResourceLocations(ResourcePaths.PATH_TO_PHOTO_STORAGE_FOR_RESOURCE_HANDLER);
    }

    @Bean
    public FilterRegistrationBean<HiddenHttpMethodFilter> hiddenHttpMethodFilter() {
        FilterRegistrationBean<HiddenHttpMethodFilter> filterRegistrationBean = new FilterRegistrationBean<>(new HiddenHttpMethodFilter());
        filterRegistrationBean.setUrlPatterns(List.of("/*"));
        return filterRegistrationBean;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();

        factory.setMaxFileSize(DataSize.ofMegabytes(5));
        factory.setMaxRequestSize(DataSize.ofMegabytes(5));

        return factory.createMultipartConfig();
    }
}
