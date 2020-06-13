import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Injectable } from '@angular/core';
import { UserService } from './user.service';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate{
    
    constructor(
        private router: Router,
        private userService: UserService
    ){ }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
        const currentUser = this.userService.currentUserValue;

        if(currentUser){
            console.log("Found a valid user", currentUser, "Route may activate");
            return true;
        }

        console.log("No valid user found! Redirecting to login page");
        this.router.navigate(['/login'], {queryParams: {returnUrl: state.url} });
        return false;
    }
}