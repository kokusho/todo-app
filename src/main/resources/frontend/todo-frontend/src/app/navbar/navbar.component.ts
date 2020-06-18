import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  username: string;

  constructor(private router: Router, private userService: UserService,) { }

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
