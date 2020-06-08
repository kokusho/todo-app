import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../models/User';
import { Router } from '@angular/router';


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

  constructor(public userService: UserService, private router: Router) {}

  ngOnInit(): void {
    //TODO add Validators. to FormControl
    
  }

  loginSubmit(userService: UserService): void {
    console.log("FORM VALUES: ", this.loginForm.value);
    let user: User = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password,
    }
    userService.doLoginUser(user, this.loginForm.value.rememberMe).subscribe(result => {
      console.log("successfully logged in..");
      this.router.navigate(["/dashboard"]);
    });
  }

}
