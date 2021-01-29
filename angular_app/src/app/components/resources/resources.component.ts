import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Resources} from '../../model/resources';
import {UserAction} from '../../model/user-action';
import {Cost} from '../../model/cost';
import {CostService} from '../../core/cost.service';

@Component({
    selector: 'app-resources',
    templateUrl: './resources.component.html',
    styleUrls: ['./resources.component.scss']
})
export class ResourcesComponent implements OnInit {
    private resources: Resources;
    private userAction: UserAction;
    private sawmillCost : Cost;
    private stonepitCost : Cost;
    private goldmineCost : Cost;

    constructor(private _router: Router,
                private _accountService: AccountService,
                private _costService: CostService) {
    }

    ngOnInit() {
        this.resources = this._accountService.userAccount.resources;
        this.sawmillCost = this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == "Sawmill"), this.resources.sawmillLvl);
        this.goldmineCost = this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == "Goldmine"), this.resources.goldmineLvl);
        this.stonepitCost = this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == "Stonepit"), this.resources.stonepitLvl);
    }

    submitWorkers() {
        this.userAction = new UserAction();
        this.userAction.action = 1;
        this.userAction.data = JSON.stringify(this.resources);
        this._accountService.resourcesAction(this.userAction).subscribe(
            response => {
                if (response.statusCode != 200) {
                    alert('Error on saving resources!');
                }
            },
            err => {
                alert(err);
            });
    }

    upgrade(what : string){
        this.userAction = new UserAction();
        this.userAction.action = 2;
        this.userAction.data = what;
        this._accountService.resourcesAction(this.userAction).subscribe(
            response => {
                if (response.statusCode == 432) {
                    alert('Not enough resources!');
                } else if(response.statusCode == 200){
                    alert("Ok");
                }
            },
            err => {
                alert(err);
            });
    }


    substractWorker(where: number) {
        switch (where) {
            case 1: {
                if (this.resources.sawmillWorkers > 0) {
                    this.resources.sawmillWorkers--;
                    this.resources.freeWorkers++;
                } else {
                    alert('Operation not possible');
                }
                break;
            }
            case 2: {
                if (this.resources.goldmineWorkers > 0) {
                    this.resources.goldmineWorkers--;
                    this.resources.freeWorkers++;
                } else {
                    alert('Operation not possible');
                }
                break;
            }
            case 3: {
                if (this.resources.stonepitWorkers > 0) {
                    this.resources.stonepitWorkers--;
                    this.resources.freeWorkers++;
                } else {
                    alert('Operation not possible');
                }
                break;
            }
        }
    }

    addWorker(where: number) {
        switch (where) {
            case 1: {
                if (this.resources.freeWorkers > 0) {
                    this.resources.sawmillWorkers++;
                    this.resources.freeWorkers--;
                } else {
                    alert('Operation not possible');
                }
                break;
            }
            case 2: {
                if (this.resources.freeWorkers > 0) {
                    this.resources.goldmineWorkers++;
                    this.resources.freeWorkers--;
                } else {
                    alert('Operation not possible');
                }
                break;
            }
            case 3: {
                if (this.resources.freeWorkers > 0) {
                    this.resources.stonepitWorkers++;
                    this.resources.freeWorkers--;
                } else {
                    alert('Operation not possible');
                }
            }
        }
    }
}
