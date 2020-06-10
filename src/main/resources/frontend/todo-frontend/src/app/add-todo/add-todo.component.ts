import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { TodoService } from '../todo.service';

@Component({
  selector: 'app-add-todo',
  templateUrl: './add-todo.component.html',
  styleUrls: ['./add-todo.component.css']
})
export class AddTodoComponent implements OnInit {

  addTodoForm = new FormGroup({
    'todoTitle': new FormControl(''),
  });

  constructor(private todoService: TodoService) {
  }
  
  ngOnInit(): void {
  }

  addTodo(){
    this.todoService.saveNewTodo(this.addTodoForm.value).subscribe(
      result => console.log("Added a todo ", result)
    );
  }

}
