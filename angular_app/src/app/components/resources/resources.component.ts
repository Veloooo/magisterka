import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Resources} from '../../model/resources';

@Component({
  selector: 'app-resources',
  templateUrl: './resources.component.html',
  styleUrls: ['./resources.component.scss']
})
export class ResourcesComponent implements OnInit {
    private resources: Resources;
    private isDataLoaded = false;

    constructor(private _router: Router,
                private _accountService: AccountService) {
    }

    ngOnInit() {
      this._accountService.getUserAllInfo().subscribe(
          user => {
            this.resources = user.resources;
            console.log(user.resources);
            this.isDataLoaded = true;
          }
      );
    }


}
