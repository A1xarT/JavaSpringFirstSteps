package project3.RestApi.utl;

import org.springframework.validation.BindingResult;

public class ErrorsUtil {
    public static String combineErrors(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (var field : bindingResult.getFieldErrors()) {
            stringBuilder.append(field.getField()).append(" - ").append(field.getDefaultMessage()).append(";");
        }
        return stringBuilder.toString();
    }
}
