package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validators.AfterFirstDateValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Validated
@Data
public class Film {
    @NotNull
    @NotBlank
    private final String name;
    @Size(max = 200)
    private final String description;
    @AfterFirstDateValidator
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
    private int id;
    private Set<Integer> userWhoLikeIds;

    public Film(String name, String description, LocalDate releaseDate, int duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.userWhoLikeIds = new HashSet<>();
    }

    public void setLikes(int userId) {
        userWhoLikeIds.add(userId);
    }

    public void deleteLike(int userId) {
        if (!userWhoLikeIds.contains(userId)) {
            throw new IllegalArgumentException("Не верный id user");
        }
        userWhoLikeIds.remove(userId);
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", duration=" + duration +
                ", userWhoLikeIds=" + userWhoLikeIds +
                '}';
    }
}