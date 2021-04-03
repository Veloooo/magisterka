import {Component, OnInit} from '@angular/core';
import {Population} from '../../model/population';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {UserAction} from '../../model/user-action';

@Component({
    selector: 'app-population',
    templateUrl: './population.component.html',
    styleUrls: ['./population.component.scss']
})
export class PopulationComponent implements OnInit {
    private userPopulation: Population;
    private isTrainingPossible = true;

    constructor(private _router: Router,
                private _accountService: AccountService) {
    }

    ngOnInit() {
        this.userPopulation = this._accountService.userAccount.population;
    }

    train(id: number) {
        let userAction = new UserAction();
        userAction.action = id;
        userAction.data = 'recruit';
        this._accountService.populationAction(userAction).subscribe(
            response => {
                console.log(response);
                if (response.statusCode == 200) {
                    this.userPopulation.total--;
                    if (id == 1) {
                        this.userPopulation.builder++;
                    } else if (id == 2) {
                        this.userPopulation.scientist++;
                    } else if (id == 3) {
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
