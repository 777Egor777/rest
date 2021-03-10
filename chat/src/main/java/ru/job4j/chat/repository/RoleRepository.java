package ru.job4j.chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.chat.domain.role.Role;

import java.util.Optional;

/**
 * @author Egor Geraskin(yegeraskin13@gmail.com)
 * @version 1.0
 * @since 28.02.2021
 */
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findByName(String roleName);
}
