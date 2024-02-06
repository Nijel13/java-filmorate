package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Вывели список всех фильмов");
        return films.values();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(id);
        id++;
        films.put(film.getId(), film);
        log.debug("Фильм с Id" + film.getId() + "был добавлен в список");
        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        if (!films.containsKey(film.getId())) {
            throw new IllegalArgumentException("Фильм с таким Id не существует.");
        }
        films.put(film.getId(), film);
        log.debug("Изменили фильм с Id " + film.getId());
        return film;
    }
}