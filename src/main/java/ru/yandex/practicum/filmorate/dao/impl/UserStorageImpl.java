package ru.yandex.practicum.filmorate.dao.impl;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Component("userStorageImpl")
@Primary
public class UserStorageImpl implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserStorageImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void checkUserExist(int id) {
        if (!(jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users WHERE user_id = ?", Integer.class, id) > 0)) {
            throw new IllegalArgumentException("Не верный id");
        }
    }

    @ExceptionHandler
    @Override
    public User update(User user) {
        checkUserExist(user.getId());
        jdbcTemplate.update("update users set email=?, login=?, name=?, birthday=? where user_id = ?",
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        insertFriends(user, true);
        return user;
    }

    private void insertFriends(User user, boolean update) {
        if (update) {
            String deleteFriends = "delete from friends where user_id = ?";
            jdbcTemplate.update(deleteFriends, user.getId());
        }
        if (user.getFriendStatus() != null) {
            String addFriends = "insert into friends(user_id, users_id, status) values (?, ?, ?)";
            for (Map.Entry<Integer, String> entry : user.getFriendStatus().entrySet()) {
                jdbcTemplate.update(addFriends, user.getId(), entry.getKey(), entry.getValue());
            }
        }
    }


    @Override
    public User add(User user) {
        KeyHolder key = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO users(login, name, email, birthday) VALUES(?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setTimestamp(4, Timestamp.valueOf(user.getBirthday().atStartOfDay()));
            return ps;
        }, key);

        int id = key.getKey().intValue();
        user.setId(id);

        insertFriends(user, false);
        return user;
    }

    @Override
    public void delete(int id) {
        String deleteFromUsers = "delete from users where user_id = ?";
        jdbcTemplate.update(deleteFromUsers, id);

        String deleteFromLikes = "delete from likes where user_id = ?";
        jdbcTemplate.update(deleteFromLikes, id);
    }

    @SneakyThrows
    @Override
    public User getById(int id) {
        checkUserExist(id);
        SqlRowSet rs = jdbcTemplate.queryForRowSet(
                "SELECT * FROM users WHERE user_id = ?", id);

        if (rs.next()) {
            User user = new User(
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    Objects.requireNonNull(rs.getDate("birthday")).toLocalDate());
            user.setId(rs.getInt("user_id"));

            HashMap<Integer, List<HashMap<Integer, String>>> friend = makeFriend();
            friend.getOrDefault(user.getId(), Collections.emptyList())
                    .forEach(userFriendMap ->
                            userFriendMap.forEach(user::setFriendStatus)
                    );

            return user;
        }
        return null;
    }

    @SneakyThrows
    @Override
    public Collection<User> get() {
        SqlRowSet rs = jdbcTemplate.queryForRowSet(
                "SELECT * FROM users");

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User(
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    Objects.requireNonNull(rs.getDate("birthday")).toLocalDate());
            user.setId(rs.getInt("user_id"));

            users.add(user);
        }

        HashMap<Integer, List<HashMap<Integer, String>>> friend = makeFriend();

        if (!friend.isEmpty()) {
            users.forEach(user -> {
                friend.getOrDefault(user.getId(), Collections.emptyList())
                        .forEach(userFriendMap -> userFriendMap.forEach(user::setFriendStatus));
            });
        }

        return users;
    }


    private HashMap<Integer, List<HashMap<Integer, String>>> makeFriend() {
        SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT * FROM friends");

        HashMap<Integer, List<HashMap<Integer, String>>> userFriends = new HashMap<>();

        while (rs.next()) {
            int userId = rs.getInt("user_id");
            int userFriendsId = rs.getInt("users_id");
            String status = rs.getString("status");

            HashMap<Integer, String> friendsWithStatus = new HashMap<>();
            friendsWithStatus.put(userFriendsId, status);

            if (userFriends.containsKey(userId)) {
                userFriends.get(userId).add(friendsWithStatus);
            } else {
                List<HashMap<Integer, String>> listOfFriends = new ArrayList<>();
                listOfFriends.add(friendsWithStatus);
                userFriends.put(userId, listOfFriends);
            }
        }

        return userFriends;
    }

    @Override
    public Set<User> getCommonFriends(Set<Integer> friendsId) {
        if (friendsId.isEmpty()) {
            return Collections.emptySet();
        }
        Collection<User> users = get();
        Set<Integer> usersIds = users.stream().map(User::getId).collect(Collectors.toSet());

        Set<Integer> commonIds = new HashSet<>(usersIds);
        commonIds.retainAll(friendsId);

        return users.stream()
                .filter(user -> commonIds.contains(user.getId()))
                .collect(Collectors.toSet());
    }
}