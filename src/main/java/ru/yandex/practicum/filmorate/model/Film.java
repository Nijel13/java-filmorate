package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.validators.MinimumDate;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Film.
 */
@Validated
@Data
@ToString
public class Film {

    private int id;
    @NotNull
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Длина описания фильма должна быть не больше {max} символов")
    private String description;
    @MinimumDate
    private LocalDate releaseDate;
    @Positive(message = "Продолжительность фильма должна быть положительной")
    private int duration;

    public Film(int id, String name, String description, LocalDate releaseDate, int duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}