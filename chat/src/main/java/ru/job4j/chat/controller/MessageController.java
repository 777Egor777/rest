package ru.job4j.chat.controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.chat.domain.message.Message;
import ru.job4j.chat.domain.message.MessageResponseEntity;
import ru.job4j.chat.domain.room.Room;
import ru.job4j.chat.domain.user.UserResponseEntity;
import ru.job4j.chat.repository.MessageRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
@RestController
@RequestMapping("/message")
public class MessageController {
    private final String USER_API_ID = "http://localhost:8080/user/{id}";
    private final String ROOM_API_ID = "http://localhost:8080/room/{id}";

    private final MessageRepository rep;
    private final RestTemplate rest;

    public MessageController(MessageRepository rep, RestTemplate rest) {
        this.rep = rep;
        this.rest = rest;
    }

    private UserResponseEntity getUserById(int id) {
        return rest.exchange(
                USER_API_ID,
                HttpMethod.GET,
                null,
                UserResponseEntity.class,
                id
        ).getBody();
    }

    private Room getRoomById(int id) {
        return rest.exchange(
                ROOM_API_ID,
                HttpMethod.GET,
                null,
                Room.class,
                id
        ).getBody();
    }

    private MessageResponseEntity transformToResponseEntity(Message message) {
        UserResponseEntity user = getUserById(message.getUserId());
        Room room = getRoomById(message.getRoomId());
        MessageResponseEntity entity = new MessageResponseEntity();
        entity.setContent(message.getContent());
        entity.setUser(user.getName());
        entity.setRoom(room.getName());
        return entity;
    }

    @GetMapping("/")
    public List<MessageResponseEntity> findAll() {
        List<MessageResponseEntity> result = new LinkedList<>();
        rep.findAll().forEach(message -> result.add(transformToResponseEntity(message)));
        return result;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponseEntity> findById(@PathVariable int id) {
        var message = rep.findById(id);
        return new ResponseEntity<>(
                transformToResponseEntity(message.orElse(new Message())),
                message.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/room/{id}")
    public List<MessageResponseEntity> findByRoom(@PathVariable int id) {
        List<MessageResponseEntity> result = new LinkedList<>();
        rep.findAllByRoomId(id).forEach(msg -> result.add(transformToResponseEntity(msg)));
        return result;
    }

    @PostMapping("/")
    public ResponseEntity<Message> create(@RequestBody Message message) {
        message = rep.save(message);
        return new ResponseEntity<>(
                message,
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Message message) {
        rep.save(message);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rep.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
