package ru.job4j.auth.controller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.domain.Report;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
@RestController
@RequestMapping("/report")
public class ReportsController {
    private final RestTemplate rest;

    public ReportsController(RestTemplate rest) {
        this.rest = rest;
    }

    public final String API = "http://localhost:8080/person/";
    public final String API_ID = "http://localhost:8080/person/{id}";

    @GetMapping("/")
    public List<Report> findAll() {
        List<Report> rsl = new LinkedList<>();
        List<Person> persons = rest.exchange(
                API,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Person>>() {
                }
        ).getBody();
        for (Person person : persons) {
            Report report = Report.of(1, "First", person);
            rsl.add(report);
        }
        return rsl;
    }
    
    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person p = rest.postForObject(API, person, Person.class);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(API_ID, id);
        return ResponseEntity.ok().build();
    }
}
