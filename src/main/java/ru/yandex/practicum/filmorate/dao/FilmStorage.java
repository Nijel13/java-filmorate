package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface FilmStorage {
    Film update(Film film);

    Film add(Film film);

    void delete(int id);

    Film getById(int id);

    Collection<Film> get();

    Collection<Film> getTop(int count);

    Film.GenreWrapper genreById(int id);

    List<Film.GenreWrapper> genre();

    List<Film.MpaWrapper> mpa();

    Film.MpaWrapper mpaById(int id);
}