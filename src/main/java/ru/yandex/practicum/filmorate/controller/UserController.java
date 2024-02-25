package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/users")
@Slf4j
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}/friends")
    public Collection<User> getUserFriends(@PathVariable int id) {
        return userService.getUserFriends(id);
    }

    @PutMapping("/{idUser}/friends/{newFriendToUserId}")
    public void addFriend(@PathVariable int idUser,
                          @PathVariable int newFriendToUserId) {
        userService.addFriend(idUser, newFriendToUserId);
    }

    @DeleteMapping("/{idUser}/friends/{removeFriendToUserId}")
    public void deleteFriend(@PathVariable int idUser,
                             @PathVariable int removeFriendToUserId) {
        userService.deleteFriend(idUser, removeFriendToUserId);
    }

    @GetMapping("/{userId}/friends/common/{userCommonFriendId}")
    public Set<User> getCommonFriends(@PathVariable int userId,
                                      @PathVariable int userCommonFriendId) {
        return userService.getCommonFriends(userId, userCommonFriendId);
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}