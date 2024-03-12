package pl.project.api.controller.addresses;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.File;

public class ResourcePaths {
    public static final String URL_TO_PHOTO_STORAGE_WITH_FORMATTER = "/dish_photos/%s";

    public static final String PATH_TO_PHOTO_STORAGE_WITH_FORMATTER =
            new File("./").getAbsolutePath() +  "/src/main/resources/storage/dish_photo/%s";
    public static final String PATH_TO_PHOTO_STORAGE_FOR_RESOURCE_HANDLER = "file:///" + PATH_TO_PHOTO_STORAGE_WITH_FORMATTER.formatted("");

    // LATER I should probably change path for DOCKER
//    public static final String PATH_TO_PHOTO_STORAGE_DOCKER_WITH_FORMATTER =
//            new File("./").getAbsolutePath() +  "/src/main/resources/storage/dish_photo/%s";

}
