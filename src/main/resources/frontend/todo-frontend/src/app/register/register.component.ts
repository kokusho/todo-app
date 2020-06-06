import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { UserService } from '../user-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor( userService : UserService ) {

  }

  ngOnInit(): void {
    this.registerForm = new FormGroup({
        'username': new FormControl(''),
        'password': new FormControl(''),
    });
  }

  registerSubmit(){
    console.log("Submitting register form", this.registerForm.value);
    let user : User = {
      username: this.registerForm.value.username,
      password: this.registerForm.value.password,
    }

    this.userService.registerUser( user );
  }

}
