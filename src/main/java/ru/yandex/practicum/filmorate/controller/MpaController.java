package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MpaController {

    private final FilmService filmService;

    @GetMapping("/mpa")
    public List<Film.MpaWrapper> getMpa() {
        return filmService.getMpa();
    }

    @GetMapping("/mpa/{id}")
    public Film.MpaWrapper getMpaById(@PathVariable int id) {
        return filmService.getMpaById(id);
    }
}