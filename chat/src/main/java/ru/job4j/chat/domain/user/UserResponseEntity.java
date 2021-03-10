package ru.job4j.chat.domain.user;

import lombok.Data;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 04.03.2021
 */
@Data
public class UserResponseEntity {
    private int id;
    private String name;
    private String password;
    private String role;

    public UserResponseEntity(int id, String name, String password, String role) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public UserResponseEntity() {
    }
}
