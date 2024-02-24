package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final InMemoryUserStorage inMemoryUserStorage;

    public Collection<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    public User getUserById(int id) {
        return inMemoryUserStorage.getUserById(id);
    }

    public User addUser(User user) {
        log.info("Пользователь {} добавлен", user);
        return inMemoryUserStorage.addUser(user);
    }

    public User updateUser(User user) {
        log.info("Пользователь {} обновлен", user);
        return inMemoryUserStorage.updateUser(user);
    }

    public void deleteUser(int id) {
        inMemoryUserStorage.deleteUser(id);
    }

    public LinkedHashSet<User> getUserFriends(int id) {
        User user = inMemoryUserStorage.getUserById(id);
        Set<Integer> usersId = user.getFriends();
        Set<User> commonFriends = new HashSet<>();
        for (Integer integer : usersId) {
            User users = inMemoryUserStorage.getUserById(integer);
            commonFriends.add(users);
        }
        return commonFriends.stream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }


    public Set<User> getCommonFriends(int userId,
                                      int userCommonFriendId) {
        List<Integer> numbers = new ArrayList<>();
        numbers.addAll(inMemoryUserStorage.getUserById(userId).getFriends());
        numbers.addAll(inMemoryUserStorage.getUserById(userCommonFriendId).getFriends());

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
            Set<User> commonFriends = new HashSet<>();
            for (Integer integer : id) {
                User user = inMemoryUserStorage.getUserById(integer);
                commonFriends.add(user);
            }
            return commonFriends;
        } else {
            return new HashSet<>();
        }
    }

    public void addFriend(int idUser,
                          int newFriendToUserId) {

        User user1 = inMemoryUserStorage.getUserById(idUser);
        User user2 = inMemoryUserStorage.getUserById(newFriendToUserId);

        user2.addFriend(idUser);
        user1.addFriend(newFriendToUserId);
    }

    public void deleteFriend(int idUser,
                             int removingFriendId) {

        User user1 = inMemoryUserStorage.getUserById(idUser);
        user1.deleteFriend(removingFriendId);

        User user2 = inMemoryUserStorage.getUserById(removingFriendId);
        user2.deleteFriend(idUser);
    }
}