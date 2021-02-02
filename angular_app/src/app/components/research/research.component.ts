import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Research} from '../../model/research';
import {ModelEntity} from '../../model/model-entity';
import {CostService} from '../../core/cost.service';
import {UserAction} from '../../model/user-action';
import {UserAccount} from '../../model/user-account';
import moment = require('moment');

@Component({
    selector: 'app-research',
    templateUrl: './research.component.html',
    styleUrls: ['./research.component.scss']
})
export class ResearchComponent implements OnInit {

    private research: Research;
    private researchEntities: ModelEntity[] = [];
    private currentlyUpgrading: string;
    private userAccount: UserAccount;
    private percent: number;
    private remainingTime: number;
    private remainingTimeString: string;

    constructor(private _router: Router,
                private _accountService: AccountService,
                private _costService: CostService) {
    }

    ngOnInit() {
        this.userAccount = this._accountService.userAccount;
        this.research = this.userAccount.research;
        this.currentlyUpgrading = this.userAccount.researchQueue;
        const researchBuilding: ModelEntity = {
            level: this.research.building,
            name: 'Building',
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == 'Building'),this.userAccount.research.building)
        };
        const researchAgriculture: ModelEntity = {
            level: this.research.agriculture,
            name: 'Agriculture',
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == 'Agriculture'),this.userAccount.research.agriculture)
        };
        const researchLogistics: ModelEntity = {
            level: this.research.logistics,
            name: 'Logistics',
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == 'Logistics'),this.userAccount.research.logistics)
        };
        const researchAttack: ModelEntity = {
            level: this.research.attack,
            name: 'Attack',
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == 'Attack'),this.userAccount.research.attack)
        };
        const researchArmour: ModelEntity = {
            level: this.research.armour,
            name: 'Armour',
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == 'Armour'),this.userAccount.research.armour)
        };
        const researchMining: ModelEntity = {
            level: this.research.mining,
            name: 'Mining',
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == 'Mining'),this.userAccount.research.mining)
        };
        this.researchEntities.push(researchBuilding);
        this.researchEntities.push(researchAgriculture);
        this.researchEntities.push(researchLogistics);
        this.researchEntities.push(researchAttack);
        this.researchEntities.push(researchArmour);
        this.researchEntities.push(researchMining);
        setInterval(() => {
            this.updateQueue();
        }, 1000);
    }

    upgrade(what: string) {
        let userAction: UserAction = new UserAction();

        if (this.currentlyUpgrading != null) {
            userAction.action = 2;
        } else {
            userAction.action = 1;
        }
        userAction.data = what;
        this._accountService.researchAction(userAction).subscribe(
            response => {
                if (response.statusCode == 432) {
                    alert('Not enough resources!');
                } else if (response.statusCode == 200) {
                    location.reload();
                    alert('Ok');
                }
            },
            err => {
                alert(err);
            });
    }

    updateQueue() {
        if (this.userAccount.researchQueue) {
            this.percent = Math.floor((new Date().getTime() - new Date(this.userAccount.researchStartTime).getTime()) / (new Date(this.userAccount.researchFinishTime).getTime()
                - new Date(this.userAccount.researchStartTime).getTime()) * 100);
            this.remainingTime = moment(this.userAccount.researchFinishTime).diff(moment());
            if (this.remainingTime >= 0)
                this.setDateString();
        }
    }

    setDateString() {
        const hours: number = Math.floor(this.remainingTime / 7200000);
        const minutes: number = Math.floor((this.remainingTime % 7200000) / 60000);
        const seconds: number = Math.floor(((this.remainingTime % 7200000) % 60000) / 1000);
        this.remainingTimeString = (hours < 10 ? '0' + hours : hours + '').concat(':').concat(minutes < 10 ? '0' + minutes : minutes + '').concat(':').concat(seconds < 10 ? "0" + seconds : seconds + "");
    }
}
