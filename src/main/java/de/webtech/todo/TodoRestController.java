package de.webtech.todo;

import de.webtech.entities.Todo;
import de.webtech.entities.TodoTitle;
import de.webtech.entities.User;
import de.webtech.exceptions.BadRequestException;
import de.webtech.shiro.SecurityUtilsWrapper;
import de.webtech.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    SecurityUtilsWrapper securityUtilsWrapper;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public Page<Todo> getTodos(@RequestParam(name = "p", defaultValue = "0") int page, @RequestParam(name = "i", defaultValue = "20") int pageSize ){
        Optional<User> userOptional = userRepository.findById(securityUtilsWrapper.getPrincipal());
        if(userOptional.isEmpty()){
            return null; // TODO improve error handling
        }
        return todoRepository.findAllByAssignedUser(PageRequest.of(page, pageSize), userOptional.get());
    }

    @PostMapping("/")
    public Todo saveTodo(@RequestBody TodoTitle todoTitle){
        Todo todo = new Todo();
        todo.setTitle(todoTitle.getTodoTitle());
        todo.setDone(false);
        Optional<User> userOptional = userRepository.findById(securityUtilsWrapper.getPrincipal());
        todo.setAssignedUser(userOptional.get());
        return this.todoRepository.save(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable("id") Long todoId, @RequestBody @Valid Todo todo){
        todo.setId( todoId );
        return this.todoRepository.save(todo);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTodo(@PathVariable("id") Long todoId){
        this.todoRepository.deleteById(todoId);
        return true;
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathVariable("id") Long id){
        Optional<Todo> todoOpt = this.todoRepository.findById(id);
        if(todoOpt.isPresent()){
            return todoOpt.get();
        }
        return null; //TODO might wanna throw an exception
    }

    @PostMapping("/{id}")
    public Todo reassignTodo(@PathVariable("id") Long todoId, @RequestBody String newAssignee){
        Optional<Todo> todoOpt = todoRepository.findById(todoId);
        if(todoOpt.isEmpty()){
            //TODO add error handling
        }
        Optional<User> newAssigneeOpt = userRepository.findById(newAssignee);
        if(newAssigneeOpt.isEmpty()){
            //TODO add even more error handling
        }
        Todo t = todoOpt.get();
        t.setAssignedUser(newAssigneeOpt.get());
        return todoRepository.save(t);
    }

    @GetMapping("/fakeData")
    public Set<Todo> fakeDataIntoDB(){
        Set<Todo> generatedTodos = new HashSet<>();
        for (User user : userRepository.findAll()) {
            for(int i=0; i<20; i++){
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

    @ExceptionHandler({BadRequestException.class})
    public void handleBadRequestException(){

    }
}
