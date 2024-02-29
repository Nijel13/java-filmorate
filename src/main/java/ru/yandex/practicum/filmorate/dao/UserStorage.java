package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.Set;

public interface UserStorage {

    User update(User user);

    User add(User user);

    void delete(int id);

    User getById(int id);

    Collection<User> get();

    Set<User> getCommonFriends(Set<Integer> id);
}