package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

public interface IUserService {
    Collection<User> getUsers();

    User getUserById(int id);

    User addUser(User user);

    User updateUser(User user);

    void deleteUser(int id);

    LinkedHashSet<User> getUserFriends(int id);

    Set<User> getCommonFriends(int userId, int userCommonFriendId);

    void addFriend(int idUser, int newFriendToUserId);

    void deleteFriend(int idUser, int removingFriendId);
}
