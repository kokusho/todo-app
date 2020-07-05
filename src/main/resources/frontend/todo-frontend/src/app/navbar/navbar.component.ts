import {Component, OnInit, Output,EventEmitter} from '@angular/core';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  @Output() delete = new EventEmitter<boolean>();
  username: string;
  checker : boolean;


  constructor(private router: Router, private userService: UserService,) { }

  ngOnInit(): void {
    this.checker = true;
    this.userService.whoAmI().subscribe(user => {
      this.username = user.username;
    }),
    error => {
      console.log("who am i returned an error redirecting to login page");
      this.router.navigate(["/login"]);
    }
  }

  logoutUser(): void{
    this.userService .doLogoutUser().subscribe(result => {
      this.checker = false;
      this.delete.emit(this.checker);

      this.router.navigate(["/login"]);
    });
  }

}
