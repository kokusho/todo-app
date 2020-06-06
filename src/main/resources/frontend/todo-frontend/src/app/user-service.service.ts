import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './models/User';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

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
        tap((newUser: User) => this.log('User logged in: ${newUser.username}'),
        catchError(this.handleError<User>('loginUser'));
       );
    }

    registerUser( u : User): Observable<User>{
      return this.http.post<User>(this.userUrl + "/register", u, this.httpOptions)
       .pipe(
        tap((newUser: User) => this.log('Successfully registered a new user: ${newUser.username}'),
        catchError(this.handleError<User>('registerUser'));
       );
    }

    handleError(functionName: string){
      //TODO improve this
      console.error(functionName, " threw an error");
      this.log(functionName + " threw an error");
    }
}
