package de.webtech.todo;

import de.webtech.util.PagingResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class TodoRestController {

    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/todos")
    public PagingResult<Todo> getTodos(){
        PagingResult<Todo> result = new PagingResult<>();


        return result;
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
