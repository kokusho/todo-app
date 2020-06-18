import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from './models/User';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Observable, of, BehaviorSubject } from 'rxjs';
import { MessageType, Message } from './models/Message';
import { AssigneeList } from './models/AssigneeList';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { 
      this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
      this.currentUser = this.currentUserSubject.asObservable();
    }

    public get currentUserValue(): User{
      return this.currentUserSubject.value;
    }

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

    private userUrl = "rest/users"; //URL to web api

    doLoginUser(u: User, rememberMe: Boolean): Observable<User>{
      return this.http.post<User>(this.userUrl + "/login?rememberMe=" + rememberMe, u, this.httpOptions)
       .pipe(
        tap((newUser: User) => {
          this.log(`User logged in: ${newUser.username}`, MessageType.Success)
          localStorage.setItem('currentUser', JSON.stringify(newUser));
          this.currentUserSubject.next(newUser);
        }),
        catchError(this.handleError<User>('loginUser'))
       );
    }

    doLogoutUser(): Observable<Boolean>{
      return this.http.get<Boolean>(this.userUrl + "/logout").pipe(
        tap( (result: Boolean) => { 
          this.log('Successfully logged out', MessageType.Success);
          localStorage.removeItem('currentUser');
          this.currentUserSubject.next(null); 

        }),
        catchError(this.handleError<Boolean>('logoutUser'))
      );
    }

    registerUser( u : User): Observable<User>{
      console.log("Calling backend to register a user", u);
      return this.http.post<User>(this.userUrl + "/register", u, this.httpOptions)
       .pipe(
        tap((newUser: User) => this.log(`Successfully registered a new user: ${newUser.username}`, MessageType.Success)),
        catchError(this.handleError<User>('registerUser'))
       );
    }

    whoAmI(): Observable<User>{
      console.log("Getting whoAmI");
      return this.http.get(this.userUrl + "/whoami")
        .pipe(
          tap( (user: User) => console.log("WhoAmI", user)),
          catchError(this.handleError<User>('whoAmI')),
        );
    } 

    getPotentialAssignees(): Observable<AssigneeList>{
      console.log("Fetching potential assignees");
      return this.http.get<AssigneeList>(this.userUrl + "/potentialAssignees")
        .pipe(
          tap( (assigneeList: AssigneeList) => console.log("Found following assignees", assigneeList)),
          catchError(this.handleError<AssigneeList>('getPotentialAssignees')),
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
