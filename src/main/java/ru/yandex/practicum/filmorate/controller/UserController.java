package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 1;

    @GetMapping
    public Collection<User> findAll() {
        log.debug("Вывели список всех пользователей");
        return users.values();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("Адрес электронной почты не может быть пустым.");
        }
        if (!user.getEmail().contains("@")) {
            throw new ValidationException("Неверный адрес электронной почты.");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() && (user.getLogin().contains(" "))) {
            throw new ValidationException("Неверный логин.");
        }
        if (user.getName().isBlank() || user.getName() == null) {
        user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        user.setId(id);
        id++;
        users.put(user.getId(), user);
        log.debug("Добавили пользователя с Id " + user.getId());
        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("Пользователя с таким Id не существует.");
        }
        users.put(user.getId(), user);
            log.debug("Изменили пользователя с Id " + user.getId());
            return user;
    }
}