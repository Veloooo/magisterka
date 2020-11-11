import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';
import { AuthService } from './auth-service.component';

@Injectable()
export class GameRouteGuard implements CanActivate {
    constructor(private _authService: AuthService) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this._authService.isLoggedIn();
    }
}
