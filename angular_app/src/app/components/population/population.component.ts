import {Component, OnInit} from '@angular/core';
import {UserPopulation} from '../../model/user-population';
import {UserAccount} from '../../model/user-account';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';

@Component({
    selector: 'app-population',
    templateUrl: './population.component.html',
    styleUrls: ['./population.component.scss']
})
export class PopulationComponent implements OnInit {
    private userPopulation: UserPopulation;
    private isDataLoaded = false;

    constructor(private _router: Router,
                private _accountService: AccountService) {
    }

    ngOnInit() {
        this._accountService.getUserAllInfo().subscribe(
            user => {
                this.userPopulation = user.population;
                this.isDataLoaded = true;
            }
        );
    }

}
