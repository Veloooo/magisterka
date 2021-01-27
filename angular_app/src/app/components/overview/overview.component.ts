import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {UserAccount} from '../../model/user-account';
import moment = require('moment');

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {

  private account: UserAccount;
  private isDataLoaded = false;
  private now: number;

  constructor(private _router: Router,
              private _accountService: AccountService) {
    setInterval(() => {
      this.calculateDiff();
    }, 1);
  }

  ngOnInit() {
    this._accountService.getUserAllInfo().subscribe(
        user => {
          this.account = user;
          console.log(user.events);
          this.isDataLoaded = true;
        }
    );

  }

  calculateDiff(){
    this.account.events.forEach(e => e.timeRemaining = moment(e.eventDate).diff(moment()));
  }

}
