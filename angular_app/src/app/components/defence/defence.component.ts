import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Defence} from '../../model/defence';

@Component({
  selector: 'app-defence',
  templateUrl: './defence.component.html',
  styleUrls: ['./defence.component.scss']
})
export class DefenceComponent implements OnInit {

  private defence: Defence;
  private isDataLoaded = false;

  constructor(private _router: Router,
              private _accountService: AccountService) {
  }

  ngOnInit() {
    this._accountService.getUserAllInfo().subscribe(
        user => {
          this.defence = user.defence;
          this.isDataLoaded = true;
        }
    );
  }

}
