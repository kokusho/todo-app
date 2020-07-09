package de.webtech.user;

import de.webtech.entities.Role;
import de.webtech.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByRoles(Role role);
}
