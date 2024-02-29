package ru.yandex.practicum.filmorate.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validators.AfterFirstDateValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Validated
@Data
public class Film {
    private int id;
    @NotNull
    @NotBlank
    private final String name;
    @Size(max = 200)
    private final String description;
    @AfterFirstDateValidator
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
    private Set<Integer> userWhoLikeIds;
    @JsonProperty("mpa")
    private MpaWrapper mpa;
    @JsonProperty("genres")
    private Set<GenreWrapper> genres = new HashSet<>();

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.userWhoLikeIds = new HashSet<>();
    }

    public void setMpa(MpaWrapper mpa) {
        this.mpa = mpa;
    }

    public void setGenre(GenreWrapper genre) {
        genres.add(genre);
    }

    public void setLikeToFilm(int id) {
        userWhoLikeIds.add(id);
    }

    public void setLikes(Set<Integer> usersId) {
        userWhoLikeIds.addAll(usersId);
    }

    public void deleteLike(int userId) {
        if (!userWhoLikeIds.contains(userId)) {
            throw new IllegalArgumentException("Не верный id user");
        }
        userWhoLikeIds.remove(userId);
    }

    @Data
    public static class MpaWrapper {
        private int id;
        private String name;
    }

    @Data
    public static class GenreWrapper {
        private int id;
        private String name;
    }
}