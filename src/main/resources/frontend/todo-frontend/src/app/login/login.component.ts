import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

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

  constructor() {}

  ngOnInit(): void {
    //TODO add Validators. to FormControl
    
  }

  loginSubmit(): void {
    console.log("FORM VALUES: ", this.loginForm.value);
    //TODO do the login call here
  }

}
