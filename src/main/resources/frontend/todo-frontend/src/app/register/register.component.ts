import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../models/User';

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

  constructor(public userService: UserService ) {
  }

  ngOnInit(): void {
  }

  registerSubmit( userService: UserService ){
    console.log("Submitting register form", this.registerForm.value);
    let user: User = {
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
    }

    userService.registerUser( user ).subscribe(val => console.log("VAL: ", val));;
  }

}
