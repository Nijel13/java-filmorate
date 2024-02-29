package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Validated
@Data
public class User {
    private int id;
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^[^\\s]+$")
    private String login;
    private String name;
    @PastOrPresent
    private LocalDate birthday;
    private Map<Integer, String> friendStatus;

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        if (name == null ||
                name.equals("")) {
            this.name = login;
        } else {
            this.name = name;
        }
        this.birthday = birthday;
        this.friendStatus = new HashMap<>();
    }

    public void deleteFriend(int id) {
        friendStatus.remove(id);
    }

    public void setFriendStatus(int id, String status) {
        friendStatus.put(id, status);
    }
}