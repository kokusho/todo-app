import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../models/User';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';
import { MessageType } from '../models/Message';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    'username': new FormControl(''),
    'password': new FormControl(''),
    'rememberMe': new FormControl(true),
  });

  constructor(public userService: UserService, private router: Router, private messageService: MessageService) {}

  ngOnInit(): void {
    
  }

  loginSubmit(userService: UserService): void {
    let user: User = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password,
    }
    userService.doLoginUser(user, this.loginForm.value.rememberMe).subscribe(
      result => {
        this.messageService.log("User successfully logged in", MessageType.Success);
        this.router.navigate(["/dashboard"]);
      },
      error => {
        this.messageService.log("Username or password is incorrect", MessageType.Danger);
      }  
    );
  }

}
