import { Component, OnInit } from '@angular/core';
import {UserPopulation} from '../../model/user-population';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Research} from '../../model/research';

@Component({
  selector: 'app-research',
  templateUrl: './research.component.html',
  styleUrls: ['./research.component.scss']
})
export class ResearchComponent implements OnInit {

  private research: Research;
  private isDataLoaded = false;

  constructor(private _router: Router,
              private _accountService: AccountService) {
  }

  ngOnInit() {
    this._accountService.getUserAllInfo().subscribe(
        user => {
          this.research = user.research;
          this.isDataLoaded = true;
        }
    );
  }

}
