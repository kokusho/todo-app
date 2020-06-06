import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor() {}

  ngOnInit(): void {
    //TODO add Validators. to FormControl
    this.loginForm = new FormGroup({
      'username': new FormControl(''),
      'password': new FormControl(''),
      'rememberMe': new FormControl(true),
    });
  }

  loginSubmit() {
    console.log("FORM VALUES: ", this.loginForm.value);
    //TODO do the login call here
  }

}
