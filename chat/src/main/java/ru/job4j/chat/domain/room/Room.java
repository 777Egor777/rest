package ru.job4j.chat.domain.room;

import lombok.Data;
import ru.job4j.chat.domain.message.Message;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
}
