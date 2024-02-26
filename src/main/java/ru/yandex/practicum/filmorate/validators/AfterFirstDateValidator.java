package ru.yandex.practicum.filmorate.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateValidatorAfterFirstDate.class)
public @interface AfterFirstDateValidator {
    String message() default "Invalid date. Date should be after 1985.01.01";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}