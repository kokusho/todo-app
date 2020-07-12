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
        tap((newUser: User) => { this.currentUserSubject.next(newUser); })
       );
    }

    doLogoutUser(): Observable<any>{
      return this.http.get(this.userUrl + "/logout").pipe(
        tap( (result: Boolean) => { 
          this.log('Successfully logged out', MessageType.Success);
          localStorage.removeItem('currentUser');
          this.currentUserSubject.next(null); 
        }),
        catchError(this.handleError<any>('logoutUser'))
      );
    }

    registerUser( u : User): Observable<User>{
      console.log("Calling backend to register a user", u);
      return this.http.post<User>(this.userUrl + "/register", u, this.httpOptions);
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
      return this.http.get<AssigneeList>(this.userUrl + "/potentialAssignees");
    }

    private handleError<T>(operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {
        console.error(error); 
        return of(result as T);
      };
    }
}
