package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UserControllerTest {
    private Validator validator;
    private UserController userController;

    @BeforeEach
    void beforeEach() {
        userController = new UserController();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void isCreateUserWithAllArgumentsNotGood() {
        User user = new User(1, "name@", " ", " ",
                LocalDate.of(2024, 1, 31));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }

    @Test
    void isUserCreateAndGetID() {
        User user = new User(1, "name@name.ru", "IgoR", "name",
                LocalDate.of(2000, 1, 31));
        userController.create(user);
        assertEquals(1, user.getId());
    }

    @Test
    void isUserWithNullNameAndEmptyNameBecomeLogin() {
        User userWithNullName = new User(1, "name@name.ru", "IgoR", null,
                LocalDate.of(2000, 1, 31));
        userController.create(userWithNullName);
        assertEquals(1, userWithNullName.getId());
        assertEquals(userWithNullName.getName(), userWithNullName.getLogin(), "NAME != LOGIN");
    }
}