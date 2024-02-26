package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;

public interface IFilmService {

    void addLikeToFilm(int filmId, int userId);

    void removeLikeFromFilm(int filmId, int userId);

    Collection<Film> getTopFilms(int count);

    Collection<Film> getFilms();

    Film getFilmById(int id);

    Film addFilm(Film film);

    Film updateFilm(Film film);

    void deleteFilm(int id);
}
