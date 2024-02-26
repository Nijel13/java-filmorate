package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

public interface FilmStorage {
    Film updateFilm(Film film);

    Film addFilm(Film film);

    void deleteFilm(int id);
}
