import { Component, OnInit } from '@angular/core';
import {Buildings} from '../../model/buildings';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Units} from '../../model/units';

@Component({
  selector: 'app-barracks',
  templateUrl: './barracks.component.html',
  styleUrls: ['./barracks.component.scss']
})
export class BarracksComponent implements OnInit {

  private units: Units;
  private isDataLoaded = false;

  constructor(private _router: Router,
              private _accountService: AccountService) {
  }

  ngOnInit() {
    this.units = this._accountService.userAccount.units;
  }
}
