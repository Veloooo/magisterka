import { Component, OnInit } from '@angular/core';
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

  constructor(private _router: Router,
              private _accountService: AccountService) {
  }

  ngOnInit() {
      this.research = this._accountService.userAccount.research;
  }

}
