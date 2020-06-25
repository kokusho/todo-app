import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { TodoService } from '../todo.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-todo',
  templateUrl: './add-todo.component.html',
  styleUrls: ['./add-todo.component.css']
})
export class AddTodoComponent implements OnInit {
  @Output() addedTodo = new EventEmitter<boolean>();

  addTodoForm = new FormGroup({
    'todoTitle': new FormControl(''),
  });

  constructor(private todoService: TodoService, private router: Router) {
  }
  
  ngOnInit(): void {
  }

  addTodo(){
    this.todoService.saveNewTodo(this.addTodoForm.value).subscribe(
      result => {
        console.log("Added a todo ", result, "Emmiting event to reload todos");
        this.addTodoForm.reset();
        this.addedTodo.emit(true);
      }
    );
  }

}
