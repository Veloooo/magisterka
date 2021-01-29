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
    private isTrainingPossible = true;
    private userAction: UserAction;

    constructor(private _router: Router,
                private _accountService: AccountService) {
    }

    ngOnInit() {
        this.userPopulation = this._accountService.userAccount.population;
    }

    train(who: number) {
        this.userAction = new UserAction();
        this.userAction.action = who;
        this.userAction.data = 'recruit';
        this._accountService.populationAction(this.userAction).subscribe(
            response => {
                console.log(response);
                if (response.statusCode == 200) {
                    this.userPopulation.total--;
                    if (who == 1) {
                        this.userPopulation.builder++;
                    } else if (who == 2) {
                        this.userPopulation.scientist++;
                    } else if (who == 3) {
                        this.userPopulation.resources++;
                    }
                }
                else{
                    alert('Not enough free workers!');
                }
            },
            err => {
                alert(err);
            });
    }
}
