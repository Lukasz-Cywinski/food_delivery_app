package pl.project.api.controller.exception;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class ExceptionMessages {
    public static String INCORRECT_INPUT_EXCEPTION = "Incorrect input params: %s_____ for resource: %s";

    public static String getFailedFields(BindingResult result) {
        return result.getAllErrors().stream()
                .map(objectError -> ((FieldError) objectError).getField())
                .map(field -> ("%s: %s;_____".formatted(field, result.getRawFieldValue(field))))
                .reduce("", (l, r) -> l + r).replaceAll(".{5}$", "");
    }
}
