package ru.yandex.practicum.filmorate.controller;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class UserControllerTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void addWrongEmailUserTest() {
        User user = new User(
                "mail.ru",
                "Ivan_Ivanovich",
                "Ivan",
                LocalDate.of(2000, 2, 22));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
    }

    @Test
    public void addWrongLogeinUserTest() {
        User user = new User(
                "ivan@mail.ru",
                "Ivan Ivanovich",
                "Ivan",
                LocalDate.of(2000, 2, 22));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
    }

    @Test
    public void addWrongDateUserTest() {
        User user = new User(
                "ivan@mail.ru",
                "Ivan_Ivanovich",
                "Ivan",
                LocalDate.of(2030, 2, 22));

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1,violations.size());
    }
}