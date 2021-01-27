import {Component, OnInit} from '@angular/core';
import {UserPopulation} from '../../model/user-population';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {UserAction} from '../../model/user-action';

@Component({
    selector: 'app-population',
    templateUrl: './population.component.html',
    styleUrls: ['./population.component.scss']
})
export class PopulationComponent implements OnInit {
    private userPopulation: UserPopulation;
    private isDataLoaded = false;
    private userAction: UserAction;

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

    train(who : number){
        this.userAction = new UserAction();
        this.userAction.action = who;
        this.userAction.data = "recruit";
        this._accountService.postUserAction(this.userAction).subscribe();
        console.log(this.userAction);
    }
}
