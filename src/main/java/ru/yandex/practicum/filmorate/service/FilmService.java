package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage inMemoryFilmStorage;

    public void addLikeToFilm(int filmId, int userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        film.setLikes(userId);
    }

    public void removeLikeFromFilm(int filmId, int userId) {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        film.deleteLike(userId);
    }

    public Collection<Film> getTopFilms(int count) {
        return inMemoryFilmStorage.getTopFilms(count);
    }

    public Collection<Film> getFilms() {
        return inMemoryFilmStorage.getFilms();
    }

    public Film getFilmById(int id) {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public Film addFilm(Film film) {
        log.info("Фильм {} добавлен", film);
        return inMemoryFilmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        log.info("Фильм {} обновлен", film);
        return inMemoryFilmStorage.updateFilm(film);
    }

    public void deleteFilm(int id) {
        inMemoryFilmStorage.deleteFilm(id);
    }
}