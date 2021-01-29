import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Buildings} from '../../model/buildings';
import {CostService} from '../../core/cost.service';
import {Cost} from '../../model/cost';

@Component({
  selector: 'app-buildings',
  templateUrl: './buildings.component.html',
  styleUrls: ['./buildings.component.scss']
})
export class BuildingsComponent implements OnInit {

  private buildings: Buildings;
  private costs : Cost[];
  constructor(private _router: Router,
              private _accountService: AccountService,
              private _costService: CostService) {
  }

  ngOnInit() {
    this.buildings = this._accountService.userAccount.buildings;
    this.costs.push(this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == "Warehouse"), this.buildings.warehouse));
  }


}
