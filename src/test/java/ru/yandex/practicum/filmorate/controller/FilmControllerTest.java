package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private Validator validator;
    private FilmController filmController;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void isCreateFilmWithAllArgumentsNotGood() {
        Film film = new Film(0, "  ", "FilmDescription AboutFilmDescription " +
                "WhenFilmDescription Too Long So FilmDescription Must Change In Order To FilmDescription Not To Be So Long Now " +
                "BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA BLA",
                LocalDate.of(1885, 1, 31), 120);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size());
    }
    @Test
    void isFilmAddAndGetID() {
        Film film = new Film(0, "FilmName", "FilmDescription",
                LocalDate.of(1985, 1, 31), 120);
        filmController.create(film);
        assertEquals(1, film.getId());
    }
    @Test
    void isFilmNotAddIfDurationNegative() {
        Film filmWithNegativeDuration = new Film(0, "FilmName", "FilmDescription",
                LocalDate.of(1985, 1, 13), -100);
        ValidationException exception = assertThrows(
                ValidationException.class, () -> filmController.create(filmWithNegativeDuration));
        assertEquals("Продолжительность фильма должна быть положительной.",
                exception.getMessage(), "No ValidationException");
    }
}