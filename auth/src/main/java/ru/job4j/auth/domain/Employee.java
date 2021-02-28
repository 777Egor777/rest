package ru.job4j.auth.domain;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String name;
    String surname;
    long inn;
    Timestamp hired;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    List<Person> accounts;
}
