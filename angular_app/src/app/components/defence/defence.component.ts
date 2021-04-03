import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Defence} from '../../model/defence';
import {ModelEntity} from '../../model/model-entity';
import {GameService} from '../../core/game-service';
import {UserAccount} from '../../model/user-account';
import {UserAction} from '../../model/user-action';
import moment = require('moment');

@Component({
    selector: 'app-defence',
    templateUrl: './defence.component.html',
    styleUrls: ['./defence.component.scss']
})
export class DefenceComponent implements OnInit {

    private defence: Defence;
    private defenceEntities: ModelEntity[] = [];
    private userAccount: UserAccount;
    private currentlyUpgrading: string;
    private percent: number;
    private remainingTime: number;
    private remainingTimeString: string;

    constructor(private _router: Router,
                private _accountService: AccountService,
                private _gameService: GameService) {
    }

    ngOnInit() {
        this.userAccount = this._accountService.userAccount;
        this.defence = this._accountService.userAccount.defence;
        this.currentlyUpgrading = this.userAccount.buildingQueue;

        this.defenceEntities.push(this._gameService.getModelEntity('Wall', this.userAccount));
        this.defenceEntities.push(this._gameService.getModelEntity('GuardTowers', this.userAccount));
        this.defenceEntities.push(this._gameService.getModelEntity('Moat', this.userAccount));

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
        this._accountService.buildingsAction(userAction).subscribe(
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
        if (this.userAccount.buildingQueue) {
            this.percent = Math.floor((new Date().getTime() - new Date(this.userAccount.buildingStartTime).getTime()) / (new Date(this.userAccount.buildingFinishTime).getTime()
                - new Date(this.userAccount.buildingStartTime).getTime()) * 100);
            this.remainingTime = moment(this.userAccount.buildingFinishTime).diff(moment());
            if (this.remainingTime >= 0)
                this.setDateString();
        }
    }

    setDateString() {
        this.remainingTimeString = this._gameService.getDateString(this.remainingTime);
    }
}
