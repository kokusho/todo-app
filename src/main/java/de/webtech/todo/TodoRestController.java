package de.webtech.todo;

import de.webtech.util.PagingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class TodoRestController {
    @GetMapping("/todos")
    public PagingResult<Todo> getTodos(){
        PagingResult<Todo> result = new PagingResult<>();

        //TODO fetch todos 

        return result;
    }
}
