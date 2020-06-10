package de.webtech.todo;

import de.webtech.entities.Todo;
import de.webtech.entities.User;
import de.webtech.util.PageableImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
@RequestMapping("/rest/todos")
public class TodoRestController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public Page<Todo> getTodos(@RequestParam(name = "p", defaultValue = "0") int page, @RequestParam(name = "i", defaultValue = "20") int pageSize ){
        PageableImpl pagingInfo = new PageableImpl(page, pageSize);
        return todoRepository.findAllByAssignedUser(pagingInfo,  new User((String) SecurityUtils.getSubject().getPrincipal()));
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
}
