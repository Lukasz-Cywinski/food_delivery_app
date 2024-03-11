package pl.project.api.controller.addresses;

import java.io.File;

public class ResourcePaths {

    public static final String PATH_FROM_ROOT_TO_PROJECT = new File("./").getAbsolutePath();
    public static final String URL_TO_PHOTO_STORAGE_WITH_FORMATTER = "/src/main/resources/static/images/storage/dish/%s";

//    public static final String PHOTO_STORAGE_WITH_FORMATTER =
//            new File("./").getAbsolutePath() +  "/src/main/resources/static/images/storage/dish/%s";
//
//    public static final String PHOTO_READ_WITH_FORMATTER = "/images/storage/dish/%s";
}
