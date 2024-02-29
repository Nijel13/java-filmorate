package ru.yandex.practicum.filmorate.controller;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilmControllerTest {

    private Validator validator;

    @Before
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void addExtremeNameFilmsTest() {
        Film film = new Film(
                null,
                "Description1",
                LocalDate.of(1999, 4, 22),
                100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    public void addExtremeYearFilmsTest() {
        Film film = new Film(
                "Film1",
                "Description1",
                LocalDate.of(1800, 4, 22),
                100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }

    @Test
    public void addExtremeDurationFilmsTest() {
        Film film = new Film(
                "Film1",
                "Description1",
                LocalDate.of(1999, 4, 22),
                -100);

        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size());
    }
}