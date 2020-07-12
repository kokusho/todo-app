import { Component, OnInit } from '@angular/core';
import { TodoService } from '../todo.service';
import { Todo } from '../models/Todo';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service';
import { AssigneeList } from '../models/AssigneeList';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-todo-details',
  templateUrl: './todo-details.component.html',
  styleUrls: ['./todo-details.component.css']
})
export class TodoDetailsComponent implements OnInit {

  public todo: Todo;
  public assigneeList: AssigneeList;
  public editing: boolean = false;

  constructor(private router: Router, private todoService: TodoService, private route: ActivatedRoute, private userService: UserService) { }

  reassignForm = new FormGroup({
    'newAssignee': new FormControl(''),
  });

  editTitleForm = new FormGroup({
    'newTitle': new FormControl(''),
  });

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      this.todoService.getTodoById(params.id).subscribe(todo => {
        this.todo = todo;
      })
    });
    this.userService.getPotentialAssignees().subscribe(
      assignees => this.assigneeList = assignees
    );
  }

  startEditing(todoId: number){
    this.editing = true;
  }

  reassignTodo(todoId: number){
    console.log("Reassigning todo", this.reassignForm.value);
    this.todoService.reassignTodo(todoId, this.reassignForm.value.newAssignee).subscribe(
      (reassignedTodo) => {
        console.log("The todo has been reassigned", reassignedTodo);
        this.router.navigate(["/dashboard"])
      }
    );
  }

  updateTitle(todoId: number){
    console.log("Updating todo title", this.editTitleForm.value);
    this.todoService.updateTodoTitle(todoId, this.editTitleForm.value.newTitle).subscribe(
      (updatedTodo) => {
        console.log("Todo has been updated!", updatedTodo);
        this.todo = updatedTodo;
      }
    )
    this.editing = false;
  }

  markAsDone(todoId: number){
    console.log("Marking todo as done ", todoId);
    this.todoService.markTodoAsDone(todoId).subscribe(
      (todo) => this.todo = todo
    )
  }

  deleteTodo(todoId: number){
    console.log("Deleting a todo", todoId);
    this.todoService.deleteTodo(todoId).subscribe(
      () => this.router.navigate(["/dashboard"])
    );
  }
}
