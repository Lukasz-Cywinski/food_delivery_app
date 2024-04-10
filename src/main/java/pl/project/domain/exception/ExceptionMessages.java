package pl.project.domain.exception;

public class ExceptionMessages {

    public final static String RESOURCE_CREATION_EXCEPTION = "Failed to crete resource: %s with identifier: %s";
    public final static String RESOURCE_READ_EXCEPTION = "Failed to found resource: %s by identifier: %s";
//    public final static String RESOURCE_MODIFICATION_EXCEPTION = "Failed to modify parameter: %s in resource: %s by identifier: %s";
    public final static String RESOURCE_MODIFICATION_EXCEPTION = "Failed to modify resource: %s by identifier: %s";
    public final static String RESOURCE_DELETE_EXCEPTION = "Failed to delete resource: %s by identifier: %s";
    public final static String DISH_PHOTO_STORAGE_SAVE_EXCEPTION = "Failed to save photo: %s";
    public final static String DISH_PHOTO_STORAGE_READ_EXCEPTION = "Failed to read photo: %s";
    public final static String DISH_PHOTO_STORAGE_UPDATE_EXCEPTION = "Failed to update photo: %s";
    public final static String DISH_PHOTO_STORAGE_DELETE_EXCEPTION = "Failed to delete photo: %s";
    public final static String REGISTRATION_EXCEPTION = "Failed to register user: %s %n Internal server error or user parameter is occupied by other user";
    public final static String SEARCH_FILTER_EXCEPTION = "Failed to use search filters";
    public final static String NO_AVAILABLE_DELIVERY_MAN_EXCEPTION = "No available delivery man";
}
