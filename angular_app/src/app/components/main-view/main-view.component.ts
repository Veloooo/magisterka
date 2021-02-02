import { Component, OnInit } from '@angular/core';
import {UserAccount} from '../../model/user-account';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import moment = require('moment');

@Component({
  selector: 'app-main-view',
  templateUrl: './main-view.component.html',
  styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent implements OnInit {

  private account: UserAccount;
  private ONE_MINUTE_IN_MILLIS = 60000;

  constructor(private _router: Router,
              private _accountService: AccountService) {
    setInterval(() => {
      this.everySecond();
    }, 1000);
  }

  ngOnInit() {
    this._accountService.getAllUSerInfoNormal(true).subscribe(account => this.account= account);
  }

  everySecond(){
    this.updateResources();

    if(this.account.buildingFinishTime){
      if(moment(this.account.buildingFinishTime).diff(moment()) < 0) {
        console.log("RELOAD");
        location.reload();
      }
    }
    if(this.account.researchFinishTime) {
      if (moment(this.account.researchFinishTime).diff(moment()) < 0)
        location.reload();
    }
  }

  updateResources(){
    const date: number = new Date().getTime();
    if(date % this.ONE_MINUTE_IN_MILLIS < 1000){
      this.account.gold += this.account.goldProduction;
      this.account.wood += this.account.woodProduction;
      this.account.stone += this.account.stoneProduction;
    }

  }
}
