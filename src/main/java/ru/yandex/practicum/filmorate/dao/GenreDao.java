package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {

    List<Genre> listGenres();

    Genre getGenreById(int id);

    List<Genre> getListGenresByMovieId(Long id);

    void setGenresForFilms(List<Film> films);

}