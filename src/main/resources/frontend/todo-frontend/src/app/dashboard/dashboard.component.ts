import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  username:string;

  constructor(private userService: UserService, private router: Router) { 
  }

  ngOnInit(): void {
    this.userService.whoAmI().subscribe(user => {
      this.username = user.username;
    })
  }

  logoutUser(): void{
    this.userService.doLogoutUser().subscribe(result => {
      if(result === true){
        this.router.navigate(["/login"]);
      }
    });
  }

}
