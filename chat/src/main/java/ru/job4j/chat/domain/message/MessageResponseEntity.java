package ru.job4j.chat.domain.message;

import lombok.Data;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 09.03.2021
 */
@Data
public class MessageResponseEntity {
    private String content;
    private String user;
    private String room;
}
