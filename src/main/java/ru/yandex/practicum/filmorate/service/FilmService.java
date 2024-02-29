package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class FilmService implements IFilmService {

    @Qualifier("filmStorageImpl")
    private final FilmStorage filmStorage;

    public List<Film.MpaWrapper> getMpa() {
        return filmStorage.mpa();
    }

    public Film.MpaWrapper getMpaById(int id) {
        return filmStorage.mpaById(id);
    }

    public List<Film.GenreWrapper> getGenre() {
        return filmStorage.genre();
    }

    public Film.GenreWrapper getGenreById(int id) {
        return filmStorage.genreById(id);
    }

    public void addLikeToFilm(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        film.setLikeToFilm(userId);
        updateFilm(film);
    }

    public void removeLikeFromFilm(int filmId, int userId) {
        Film film = filmStorage.getById(filmId);
        film.deleteLike(userId);
        updateFilm(film);
    }

    public Collection<Film> getTopFilms(int count) {
        return filmStorage.getTop(count);
    }


    public Collection<Film> getFilms() {
        return filmStorage.get();
    }

    public Film getFilmById(int id) {
        return filmStorage.getById(id);
    }

    public Film addFilm(Film film) { //add film
        log.info("Фильм {} добавлен", film);
        return filmStorage.add(film);
    }

    public Film updateFilm(Film film) { //update film
        log.info("Фильм {} обновлен", film);
        return filmStorage.update(film);
    }

    public void deleteFilm(int id) {
        filmStorage.delete(id);
    }
}