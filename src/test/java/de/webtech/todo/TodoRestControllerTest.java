package de.webtech.todo;

import de.webtech.entities.Todo;
import de.webtech.entities.User;
import de.webtech.shiro.SecurityUtilsWrapper;
import de.webtech.user.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@MockBean(SecurityUtilsWrapper.class)
@SpringBootTest
class TodoRestControllerTest {
    @Autowired
    SecurityUtilsWrapper securityUtilsWrapper;
    @Autowired
    TodoRestController todoRestController;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TodoRepository todoRepository;

    User oldAssignee;
    User newAssignee;

    @BeforeEach
    public void setup() {
        oldAssignee = userRepository.save(new User("oldAssignee", "123456789"));
        newAssignee = userRepository.save(new User("newAssignee", "123456789"));
    }

    @AfterEach
    public void cleanup() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void smokeTest() {
        assertNotNull(todoRestController);
    }

    @Test
    public void reassignTodo() {
        Mockito.when(securityUtilsWrapper.getPrincipal()).thenReturn("oldAssignee");
        Todo oldTodo = new Todo();
        oldTodo.setAssignedUser(oldAssignee);
        oldTodo.setTitle("CHECK FOR REASSIGN TODO");
        oldTodo = todoRepository.save(oldTodo);

        ResponseEntity<Object> response = todoRestController.reassignTodo(oldTodo.getId(), "newAssignee");
        assertTrue(response.getBody() instanceof Todo);
        Todo reassignedTodo = (Todo) response.getBody();
        assertEquals(oldTodo.getId(), reassignedTodo.getId());
        assertEquals("newAssignee", reassignedTodo.getAssignedUser().getUsername());
    }

    @Test
    public void testGetPagedTodos() {
        Mockito.when(securityUtilsWrapper.getPrincipal()).thenReturn("oldAssignee");
        for (int i = 0; i < 20; i++) {
            Todo t = new Todo();
            t.setAssignedUser(oldAssignee);
            t.setTitle("todo" + i);
            todoRepository.save(t);
        }
        Page<Todo> todosPage = todoRestController.getTodos(0, 5);
        assertEquals(0, todosPage.getNumber());
        assertEquals(5, todosPage.getNumberOfElements());
        assertEquals(4, todosPage.getTotalPages());
        assertEquals(20, todosPage.getTotalElements());
    }

    @Test
    public void testMarkTodoAsDone(){
        Mockito.when(securityUtilsWrapper.getPrincipal()).thenReturn("oldAssignee");
        Todo t = new Todo();
        t.setAssignedUser(oldAssignee);
        t.setTitle("MARK ME AS DONE");
        Todo saved = todoRepository.save(t);
        ResponseEntity<Object> responseEntity = todoRestController.markAsDone(saved.getId());
        assertTrue(responseEntity.getBody() instanceof Todo);
        Todo doneTodo = (Todo) responseEntity.getBody();
        assertTrue(doneTodo.isDone());
    }

}