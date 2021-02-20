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

  constructor(private _router: Router,
              private _accountService: AccountService) {

    setInterval(() => {
      this.calculateDiff();
    }, 1);
  }

  ngOnInit() {
    this.account = this._accountService.userAccount;
    console.log(this.account.playerStatistics);
  }

  calculateDiff(){
      this.account.events.forEach(e => e.timeRemaining = moment(e.eventDate).diff(moment()));
  }

}
