package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Slf4j
public class UserService implements IUserService {

    @Qualifier("userStorageImpl")
    private final UserStorage userStorage;

    public Collection<User> getUsers() {
        return userStorage.get();
    }

    public User getUserById(int id) {
        return userStorage.getById(id);
    }

    public User addUser(User user) {
        log.info("Пользователь {} добавлен", user);
        return userStorage.add(user);
    }

    public User updateUser(User user) {
        log.info("Пользователь {} обновлен", user);
        return userStorage.update(user);
    }

    public void deleteUser(int id) {
        userStorage.delete(id);
    }

    public LinkedHashSet<User> getUserFriends(int id) {
        User user = userStorage.getById(id);

        Set<Integer> usersId = user.getFriendStatus().keySet();
        Set<User> friends = new HashSet<>();

        for (Integer integer : usersId) {
            User userFriend = userStorage.getById(integer);
            if (userFriend != null) {
                friends.add(userFriend);
            }
        }

        return friends.stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public Set<User> getCommonFriends(int userId,
                                      int userCommonFriendId) {
        List<Integer> numbers = new ArrayList<>();
        Set<Integer> i = userStorage.getById(userId).getFriendStatus().keySet();
        Set<Integer> j = userStorage.getById(userCommonFriendId).getFriendStatus().keySet();

        numbers.addAll(i);
        numbers.addAll(j);

        if (!numbers.isEmpty()) {
            Map<Integer, Long> map = numbers
                    .stream()
                    .collect(
                            Collectors
                                    .groupingBy(n -> n, Collectors.counting())
                    );
            Set<Integer> id = map
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
            return userStorage.getCommonFriends(id);
        } else {
            return new HashSet<>();
        }
    }

    public void addFriend(int idUser,
                          int newFriendToUserId) {

        User user = userStorage.getById(idUser);
        User userCheck = userStorage.getById(newFriendToUserId);

        user.setFriendStatus(newFriendToUserId, "Запрос отправлен");

        updateUser(user);
    }


    public void deleteFriend(int idUser,
                             int removingFriendId) {

        User user1 = userStorage.getById(idUser);
        user1.deleteFriend(removingFriendId);

        updateUser(user1);
    }
}