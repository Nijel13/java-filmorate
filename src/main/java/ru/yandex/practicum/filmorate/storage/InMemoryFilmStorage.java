package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new ConcurrentHashMap<>();
    private int id = 1;

    public Collection<Film> getFilms() { //get all films
        return films.values();
    }

    public Film getFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new IllegalArgumentException("Неверный id фильма");
        }
        return films.get(id);
    }

    @Override
    public Film addFilm(Film film) {
        film.setId(id);
        id++;
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new IllegalStateException("Неверный id");
        }
        films.put(film.getId(), film);
        return film;
    }


    @Override
    public void deleteFilm(int id) {
        if (id > 0) {
            films.remove(id);
        } else {
            throw new IllegalStateException("Неверное значение");
        }
    }

    public Collection<Film> getTopFilms(int count) {
        return getFilms().stream().filter(Objects::nonNull).sorted((o1, o2) -> {
            int amountOfLikes1 = o1.getUserWhoLikeIds().size();
            int amountOfLikes2 = o2.getUserWhoLikeIds().size();
            return Integer.compare(amountOfLikes2, amountOfLikes1);
        }).limit(count).collect(Collectors.toList());
    }

}