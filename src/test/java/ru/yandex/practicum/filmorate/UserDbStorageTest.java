package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.dao.impl.UserStorageImpl;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testAddFriend() {
        User newUser = new User("user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        User newUser2 = new User("2user@email.ru", "2vanya123", "2Ivan Petrov", LocalDate.of(1990, 1, 1));

        UserService userService = new UserService(new UserStorageImpl(jdbcTemplate));
        userService.addUser(newUser);
        userService.addUser(newUser2);

        userService.addFriend(newUser.getId(), newUser2.getId());

        // вызываем тестируемый метод
        User savedUser = userService.getUserById(1);

        System.out.println(savedUser);

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull()
                .usingRecursiveComparison();

    }
}