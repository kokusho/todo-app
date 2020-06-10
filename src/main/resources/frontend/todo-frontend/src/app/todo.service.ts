import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Observable, of } from 'rxjs';
import { MessageType, Message } from './models/Message';
import { Page } from './models/Page';
import { Todo } from './models/Todo';

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  private todoUrl = "rest/todos";

  constructor(private http: HttpClient,
    private messageService: MessageService) { }

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  /** Log a UserService message with the MessageService */
  private log(message: string, m_type: MessageType) {
    let msg: Message = {
      message: `UserService: ${message}`,
      type: m_type
    }
    this.messageService.add(msg);
  }

  getAssignedTodos(page: number = 0, itemsPerPage: number = 20): Observable<Page<Todo>>{
    return this.http.get<Page<Todo>>(this.todoUrl+`/?p=${page}&i=${itemsPerPage}`)
      .pipe(
        tap((todoPage: Page<Todo>) => console.log("Fetched a page of todos", todoPage)),
        catchError(this.handleError<Page<Todo>>('getAssignedTodos'))
      );
  }

  getTodoById(todoId: number): Observable<Todo>{
    return this.http.get<Todo>(this.todoUrl+"/"+todoId)
      .pipe(
        tap((todo: Todo) => console.log("Fetched todo by id", todoId, todo)),
        catchError(this.handleError<Todo>("getTodoById"))
      );
  }

  saveNewTodo(todo: string): Observable<Todo>{
    return this.http.post<Todo>(this.todoUrl+"/", todo, this.httpOptions)
      .pipe(
        tap(( newTodo: Todo) => this.log(`Successfully saved a new todo: ${newTodo.title}`, MessageType.Success)),
        catchError(this.handleError<Todo>("saveNewTodo")),
      );
  }

  updateTodo(updatedTodo: Todo): Observable<Todo>{
    return this.http.post<Todo>(this.todoUrl+"/"+updatedTodo.id, updatedTodo, this.httpOptions)
      .pipe(
        tap((resultTodo) => this.log(`Successfully update todo with id: ${resultTodo.id}`, MessageType.Success)),
        catchError(this.handleError<Todo>("updateTodo"))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
  
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
  
      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`, MessageType.Danger);
  
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
