package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

public interface UserStorage {

    User updateUser(User user);

    User addUser(User user);

    void deleteUser(int id);

}
