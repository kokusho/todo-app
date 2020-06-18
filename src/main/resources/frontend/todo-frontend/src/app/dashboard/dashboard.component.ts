import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { TodoService } from '../todo.service';
import { Todo } from '../models/Todo';
import { Page } from '../models/Page';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  todoPage: Page<Todo>;

  constructor( private router: Router, private todoService: TodoService) { 
  }

  ngOnInit(): void {
    this.todoService.getAssignedTodos().subscribe(todoPage => {
      this.todoPage = todoPage;
    })
  }

  showTodoDetails(todoId: number){
    this.router.navigate(["/todo/"+todoId]);
  }

}
