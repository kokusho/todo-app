package de.webtech.todo;


import de.webtech.entities.Todo;
import de.webtech.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface TodoRepository extends PagingAndSortingRepository<Todo, Long> {
    Page<Todo> findAllByAssignedUser(Pageable pageable, User assignedUser);
}
