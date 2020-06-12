import { Component, OnInit } from '@angular/core';
import { TodoService } from '../todo.service';
import { Todo } from '../models/Todo';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-todo-details',
  templateUrl: './todo-details.component.html',
  styleUrls: ['./todo-details.component.css']
})
export class TodoDetailsComponent implements OnInit {

  todo: Todo;

  constructor(private todoService: TodoService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.todoService.getTodoById(params.id).subscribe(todo => {
        this.todo = todo;
      })
    })
  }

  markAsDone(todoId: number){
    console.log("Marking todo as done ", todoId);
  }

  editTodo(todoId: number){
    console.log("Editing a todo", todoId);
  }

  deleteTodo(todoId: number){
    console.log("Deleting a todo", todoId);
  }
}
