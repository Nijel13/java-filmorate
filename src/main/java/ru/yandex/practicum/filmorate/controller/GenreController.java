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
public class GenreController {

    private final FilmService filmService;

    @GetMapping("/genres")
    public List<Film.GenreWrapper> getGenre() {
        return filmService.getGenre();
    }

    @GetMapping("/genres/{id}")
    public Film.GenreWrapper getGenreById(@PathVariable int id) {
        return filmService.getGenreById(id);
    }

}