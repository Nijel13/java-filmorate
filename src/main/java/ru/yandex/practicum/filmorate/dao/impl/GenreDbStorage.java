package ru.yandex.practicum.filmorate.dao.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Repository
@Slf4j
@AllArgsConstructor
public class GenreDbStorage implements GenreDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Genre> listGenres() {
        String sqlQuery = "select * from GENRES";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenres);
    }

    @Override
    public Genre getGenreById(int id) {
        String sqlQuery = "select * from GENRES where GENRES_GENRES_ID =?";
        try {
            Genre genre = jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenres, id);
            return genre;
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр по id: " + id + " не найден!");
        }
    }

    @Override
    public List<Genre> getListGenresByMovieId(Long id) {
        final String sqlQuery = "SELECT DISTINCT * FROM FILM_GENRES FG INNER JOIN GENRES G " +
                "on FG.FILM_GENRES_GENRES_ID = G.GENRES_GENRES_ID WHERE FG.FILM_GENRES_FILM_ID=?";
        List<Genre> ls = jdbcTemplate.query(sqlQuery, this::mapRowToGenres, id);
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenres, id);
    }

    private Genre mapRowToGenres(ResultSet resultSet, int rowNum) throws SQLException {
        return Genre.builder()
                .id(resultSet.getInt("GENRES_GENRES_ID"))
                .name(resultSet.getString("GENRES_NAME"))
                .build();
    }

    @Override
    public void setGenresForFilms(List<Film> films) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("ids", films.stream().map(Film::getId).collect(Collectors.toList()));
        final String sqlQuery = "SELECT * FROM GENRES G join FILM_GENRES FG" +
                " on G.GENRES_GENRES_ID = FG.FILM_GENRES_GENRES_ID WHERE FG.FILM_GENRES_FILM_ID IN (:ids)";
        List<Map<String, Object>> maps = namedParameterJdbcTemplate.queryForList(sqlQuery, parameterSource);
        HashMap<Long, List<Genre>> genres = new HashMap<>();
        for (Map<String, Object> genre : maps) {
            Long filmId = Long.parseLong(genre.get("FILM_GENRES_FILM_ID").toString());
            if (!genres.containsKey(filmId)) {
                genres.put(filmId, new ArrayList<>());
            }
            genres.get(filmId).add(
                    new Genre(
                            Integer.parseInt(genre.get("GENRES_GENRES_ID").toString()),
                            genre.get("GENRES_NAME").toString()
                    ));
        }
        for (Film film : films) {
            film.setGenres(genres.get(film.getId()));
        }
    }

}