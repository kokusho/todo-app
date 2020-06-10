package de.webtech.todo;

import de.webtech.entities.Todo;
import de.webtech.entities.User;
import de.webtech.user.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/rest/todos")
public class TodoRestController {
    private static final transient Logger log = LoggerFactory.getLogger(TodoRestController.class);

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/todos")
    public Page<Todo> getTodos(@RequestParam(name = "p", defaultValue = "0") int page, @RequestParam(name = "i", defaultValue = "20") int pageSize ){
        return todoRepository.findAllByAssignedUser(PageRequest.of(page, pageSize),  new User((String) SecurityUtils.getSubject().getPrincipal()));
    }

    @GetMapping("/save") //TODO later change to post when wiring frontend
    public void testSaveTodo(){
        Todo todo = new Todo();
        todo.setTitle("this is a first todo");
        this.todoRepository.save(todo);
    }

    @GetMapping("/{id}")
    public Todo getTodo(@PathParam("id") Long id){
        Optional<Todo> todoOpt = this.todoRepository.findById(id);
        if(todoOpt.isPresent()){
            return todoOpt.get();
        }
        return null; //TODO might wanna throw an exception
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
}
