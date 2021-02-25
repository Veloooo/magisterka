import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {UserAccount} from '../../model/user-account';
import moment = require('moment');
import {GameService} from '../../core/game-service';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {

  private account: UserAccount;

  constructor(private _router: Router,
              private _accountService: AccountService,
              private _gameService: GameService) {

    setInterval(() => {
      this.calculateDiff();
    }, 1);
  }

  ngOnInit() {
    this.account = this._accountService.userAccount;
  }

  calculateDiff(){
      this.account.events.forEach(e => e.timeRemaining = moment(e.eventDate).diff(moment()));
  }

  getCityCoords(){
    let row = Math.ceil(this.account.cityPosition/4);
    let column = (this.account.cityPosition - 4 * (row - 1));
    return row + ":" + column;
  }

  getUnitNameByFraction(fraction: string, id: number){
    return this._gameService.getUnitNameByFraction(fraction, id);
  }
}
