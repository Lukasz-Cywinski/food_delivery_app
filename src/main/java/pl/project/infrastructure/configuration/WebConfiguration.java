package pl.project.infrastructure.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import pl.project.api.controller.addresses.ResourcePaths;

@Configuration
public class WebConfiguration extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/dish_photos/**")
                .addResourceLocations(ResourcePaths.PATH_TO_PHOTO_STORAGE_FOR_RESOURCE_HANDLER);
    }
}
