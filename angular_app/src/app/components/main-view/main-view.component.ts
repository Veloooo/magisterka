import { Component, OnInit } from '@angular/core';
import {UserAccount} from '../../model/user-account';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent implements OnInit {

  private account: UserAccount;
  private isDataLoaded = false;

  constructor(private _router: Router,
              private _accountService: AccountService) {
    setInterval(() => {
      this.updateResources();
    }, 1);
  }

  ngOnInit() {
    if(this._accountService.userAccount)
      this.account = this._accountService.userAccount;
    else
      this._accountService.getUserAllInfo().subscribe(
          user => {
            this.account = user;
            console.log(user.events);
            this.isDataLoaded = true;
          }
      );
  }

  updateResources(){

  }
}
