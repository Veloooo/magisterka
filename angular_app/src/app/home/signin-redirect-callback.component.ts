import { Component, OnInit } from '@angular/core';
import { AuthService } from '../core/auth-service.component';
import { Router } from '@angular/router';
import {UserAccount} from '../model/user-account';
import {state} from '@angular/animations';
import {AccountService} from '../core/account.service';

@Component({
  selector: 'app-signin-callback',
  template: `<div></div>`
})

export class SigninRedirectCallbackComponent implements OnInit {

  userAccount: UserAccount;
  constructor(private _authService: AuthService,
              private _router: Router,
              private _accountService: AccountService) {}

  ngOnInit() {
    this._authService.completeLogin().then(user => {
      this._accountService.getUserAccount().subscribe(
          user => {
              this.userAccount = user;
              console.log(this.userAccount);
              if(this.userAccount)
                  this._router.navigate(['/'], { replaceUrl: true });
              else
                  this._router.navigate(['create-account'], { replaceUrl: true});
          }
      );
    })
  }
}
