package ru.job4j.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import ru.job4j.auth.domain.Employee;
import ru.job4j.auth.domain.Person;
import ru.job4j.auth.repository.EmployeeRepository;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeRepository rep;
    private final RestTemplate rest;

    public final String PERSON_API = "http://localhost:8080/person/";
    public final String PERSON_API_ID = "http://localhost:8080/person/{id}";

    public EmployeeController(EmployeeRepository rep, RestTemplate rest) {
        this.rep = rep;
        this.rest = rest;
    }

    @GetMapping("/")
    public List<Employee> findAll() {
        return StreamSupport.stream(rep.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        Person p = rest.postForObject(PERSON_API, person, Person.class);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Person person) {
        rest.put(PERSON_API, person);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rest.delete(PERSON_API_ID, id);
        return ResponseEntity.ok().build();
    }
}
