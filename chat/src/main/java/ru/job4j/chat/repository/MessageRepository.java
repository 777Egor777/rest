package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.domain.message.Message;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
public interface MessageRepository extends CrudRepository<Message, Integer> {
    Iterable<Message> findAllByRoomId(int roomId);
}
