package de.webtech.todo;

import de.webtech.entities.Todo;
import de.webtech.entities.TodoTitle;
import de.webtech.entities.User;
import de.webtech.shiro.SecurityUtilsWrapper;
import de.webtech.user.UserRepository;
import de.webtech.util.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/rest/todos")
public class TodoRestController {
    private static final transient Logger log = LoggerFactory.getLogger(TodoRestController.class);

    @Autowired
    private SecurityUtilsWrapper securityUtilsWrapper;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/reset")
    public void Reset(){
        todoRepository.deleteAll();
    }
    @GetMapping("/")
    public Page<Todo> getTodos(@RequestParam(name = "p", defaultValue = "0") int page, @RequestParam(name = "i", defaultValue = "20") int pageSize) {
        Optional<User> userOptional = userRepository.findById(securityUtilsWrapper.getPrincipal());
        if (userOptional.isEmpty()) {
            return null; // TODO improve error handling
        }
        return todoRepository.findAllByAssignedUser(PageRequest.of(page, pageSize), userOptional.get());
    }

    @PostMapping("/")
    public Todo saveTodo(@RequestBody TodoTitle todoTitle) {
        Todo todo = new Todo();
        todo.setTitle(todoTitle.getTodoTitle());
        todo.setDone(false);
        Optional<User> userOptional = userRepository.findById(securityUtilsWrapper.getPrincipal());
        todo.setAssignedUser(userOptional.get());
        return this.todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTodo(@PathVariable("id") Long todoId, @RequestBody @Valid Todo todo) {
        Optional<Todo> byIdOpt = todoRepository.findById(todoId);
        if(byIdOpt.isEmpty()){
            return new ResponseEntity<>(new ResponseMessage("Todo to update with id "+ todoId + " was not found!"), HttpStatus.NOT_FOUND);
        }
        if(!securityUtilsWrapper.getPrincipal().equals(byIdOpt.get().getAssignedUser().getUsername())){
            return new ResponseEntity<>(new ResponseMessage("You may not update this todo!"), HttpStatus.FORBIDDEN);
        }
        todo.setId(todoId);
        return new ResponseEntity<>(this.todoRepository.save(todo), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTodo(@PathVariable("id") Long todoId) {
        Optional<Todo> todoToDeleteOpt = this.todoRepository.findById(todoId);
        if(todoToDeleteOpt.isEmpty()){
            return new ResponseEntity<>(new ResponseMessage("Todo with id " + todoId + " was not found!"), HttpStatus.NOT_FOUND);
        } else if(!todoToDeleteOpt.get().getAssignedUser().getUsername().equals(securityUtilsWrapper.getPrincipal())){
            return new ResponseEntity<>(new ResponseMessage(securityUtilsWrapper.getPrincipal() + " is not authorized to delete this todo!"), HttpStatus.FORBIDDEN);
        }
        this.todoRepository.deleteById(todoId);
        return new ResponseEntity<>(new ResponseMessage("Todo was deleted successfully"), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTodo(@PathVariable("id") Long id) {
        Optional<Todo> todoOpt = this.todoRepository.findById(id);
        if(todoOpt.isEmpty()){
            return new ResponseEntity<>(new ResponseMessage("Todo with id " + id + " was not found!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(todoOpt.get(), HttpStatus.OK);
    }

    @PostMapping("reassign/{id}")
    public ResponseEntity<Object> reassignTodo(@PathVariable("id") Long todoId, @RequestBody String newAssignee) {
        Optional<Todo> todoOpt = todoRepository.findById(todoId);
        if (todoOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Todo with id " + todoId + " was not found!"), HttpStatus.NOT_FOUND);
        }
        Optional<User> newAssigneeOpt = userRepository.findById(newAssignee);
        if (newAssigneeOpt.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("New assignee for todo cannot be found!"), HttpStatus.NOT_FOUND);
        }
        Todo t = todoOpt.get();
        t.setAssignedUser(newAssigneeOpt.get());
        return new ResponseEntity<>(todoRepository.save(t), HttpStatus.OK);
    }

    @GetMapping("/markAsDone/{id}")
    public ResponseEntity<Object> markAsDone(@PathVariable("id") Long todoId) {
        Optional<Todo> todoOptional = todoRepository.findById(todoId);
        if (todoOptional.isEmpty()) {
            return new ResponseEntity<>(new ResponseMessage("Todo with id " + todoId + " was not found!"), HttpStatus.NOT_FOUND);
        }
        Todo todo = todoOptional.get();
        if (todo.isDone()) {
            return new ResponseEntity<>(new ResponseMessage("Todo with id " + todoId + " was already marked as done!"), HttpStatus.BAD_REQUEST);
        }
        todo.setDone(true);
        return new ResponseEntity<>(todoRepository.save(todo), HttpStatus.OK);
    }

    @GetMapping("/fakeData")
    public Set<Todo> fakeDataIntoDB() {
        Set<Todo> generatedTodos = new HashSet<>();
        for (User user : userRepository.findAll()) {
            for (int i = 0; i < 20; i++) {
                Todo t = new Todo();
                t.setAssignedUser(user);
                t.setDone(Math.random() <= 0.5);
                t.setTitle("todo" + i);
                log.debug(t.toString());
                todoRepository.save(t);
                generatedTodos.add(t);
            }
        }
        return generatedTodos;
    }
}
