import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
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

  username:string;
  todoPage: Page<Todo>;

  constructor(private userService: UserService, private router: Router, private todoService: TodoService) { 
  }

  ngOnInit(): void {
    this.userService.whoAmI().subscribe(user => {
      this.username = user.username;
    })
    this.todoService.getAssignedTodos().subscribe(todoPage => {
      this.todoPage = todoPage;
    })
  }

  logoutUser(): void{
    this.userService.doLogoutUser().subscribe(result => {
      if(result === true){
        this.router.navigate(["/login"]);
      }
    });
  }

  showTodoDetails(todoId: number){
    this.router.navigate(["/todo/"+todoId]);
  }

}
