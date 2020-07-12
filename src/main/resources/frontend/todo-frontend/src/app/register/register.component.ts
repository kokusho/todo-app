import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../models/User';
import { MessageService } from '../message.service';
import { MessageType } from '../models/Message';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm = new FormGroup({
      'username': new FormControl(''),
      'password': new FormControl(''),
  });

  constructor(private router: Router, public userService: UserService, private messageService: MessageService ) {
  }

  ngOnInit(): void {
  }

  registerSubmit( userService: UserService ){
    console.log("Submitting register form", this.registerForm.value);
    let user: User = {
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
    }

    userService.registerUser( user ).subscribe(
      response => {
        this.messageService.log("User successfully registered!", MessageType.Success);
        this.router.navigate(["/login"]);
      },
      errorResponse => {
        console.log("error messages when registering: ", errorResponse, errorResponse.error);
        if(errorResponse.error != undefined && Array.isArray(errorResponse.error.messages)){
          errorResponse.error.messages.forEach(msg => {
            this.messageService.log(msg, MessageType.Danger);
          })
        }
      }
    );
  }

}
