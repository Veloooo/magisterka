import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Buildings} from '../../model/buildings';

@Component({
  selector: 'app-buildings',
  templateUrl: './buildings.component.html',
  styleUrls: ['./buildings.component.scss']
})
export class BuildingsComponent implements OnInit {

  private buildings: Buildings;
  private isDataLoaded = false;

  constructor(private _router: Router,
              private _accountService: AccountService) {
  }

  ngOnInit() {
    this._accountService.getUserAllInfo().subscribe(
        user => {
          this.buildings = user.buildings;
          this.isDataLoaded = true;
        }
    );
  }


}
