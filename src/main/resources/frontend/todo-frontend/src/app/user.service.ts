import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './models/User';
import { catchError, map, tap } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Observable, of } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

    httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

    /** Log a UserService message with the MessageService */
    private log(message: string) {
      this.messageService.add(`UserService: ${message}`);
    }

    private userUrl = "rest/users"; //URL to web api

    doLoginUser(u: User): Observable<User>{
      return this.http.post<User>(this.userUrl + "/login", u, this.httpOptions)
       .pipe(
        tap((newUser: User) => this.log('User logged in: ${newUser.username}')),
        catchError(this.handleError<User>('loginUser'))
       );
    }

    registerUser( u : User): Observable<User>{
      return this.http.post<User>(this.userUrl + "/register", u, this.httpOptions)
       .pipe(
        tap((newUser: User) => this.log('Successfully registered a new user: ${newUser.username}')),
        catchError(this.handleError<User>('registerUser'))
       );
    }

    private handleError<T>(operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {
    
        // TODO: send the error to remote logging infrastructure
        console.error(error); // log to console instead
    
        // TODO: better job of transforming error for user consumption
        this.log(`${operation} failed: ${error.message}`);
    
        // Let the app keep running by returning an empty result.
        return of(result as T);
      };
    }
}
