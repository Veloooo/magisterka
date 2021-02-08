import {Component, OnInit} from '@angular/core';
import {UserAccount} from '../../model/user-account';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import moment = require('moment');

@Component({
    selector: 'app-main-view',
    templateUrl: './main-view.component.html',
    styleUrls: ['./main-view.component.scss']
})
export class MainViewComponent implements OnInit {

    private account: UserAccount;
    private ONE_MINUTE_IN_MILLIS = 60000;
    private buildingFinishTime: Date;
    private researchFinishTime: Date;

    constructor(private _router: Router,
                private _accountService: AccountService) {
        setInterval(() => {
            this.everySecond();
        }, 1000);
    }

    ngOnInit() {
        this._accountService.getAllUSerInfoNormal(true).subscribe(account => {
            this.account = account;
            if (this.account.buildingFinishTime) {
                this.buildingFinishTime = moment(this.account.buildingFinishTime).add(1, 'm').toDate();
            }
            if (this.account.researchFinishTime) {
                this.researchFinishTime = moment(this.account.researchFinishTime).add(1, 'm').toDate();
            }
        });

    }

    everySecond() {
        this.updateResources();

        if (this.researchFinishTime) {
            if (moment(this.researchFinishTime).diff(moment()) < 0) {
                location.reload();
            }
        }
        if (this.buildingFinishTime) {
            console.log(moment(this.buildingFinishTime).diff(moment()));
            if (moment(this.buildingFinishTime).diff(moment()) < 0) {
                location.reload();
            }
        }
    }

    updateResources() {
        const date: number = new Date().getTime();
        if (date % this.ONE_MINUTE_IN_MILLIS < 1000) {
            this.account.gold += this.account.goldProduction;
            this.account.wood += this.account.woodProduction;
            this.account.stone += this.account.stoneProduction;
        }

    }
}
