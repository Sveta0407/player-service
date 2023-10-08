package com.intuit.playerservice.validator;

import com.intuit.playerservice.model.Player;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class PlayerValidation implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Player.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Player course = (Player) target;

        if(errors.getErrorCount() == 0) {
            ValidationUtils.rejectIfEmptyOrWhitespace(
                    errors, "name",
                    "error.name",
                    "Name is required.");

        }
    }
}
