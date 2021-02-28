package ru.job4j.auth.domain;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
@Data
public class Report {
    private int id;
    private String name;
    private Timestamp created;
    private Person person;

    public static Report of(int id, String name, Person person) {
        Report rep = new Report();
        rep.id = id;
        rep.name = name;
        rep.person = person;
        rep.created = new Timestamp(System.currentTimeMillis());
        return rep;
    }

}
