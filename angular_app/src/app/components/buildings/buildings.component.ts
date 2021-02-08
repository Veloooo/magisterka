import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Buildings} from '../../model/buildings';
import {ModelEntity} from '../../model/model-entity';
import {UserAccount} from '../../model/user-account';
import {GameService} from '../../core/game-service';
import {UserAction} from '../../model/user-action';
import moment = require('moment');
import {ResearchEntity} from '../../model/research-entity';

@Component({
    selector: 'app-buildings',
    templateUrl: './buildings.component.html',
    styleUrls: ['./buildings.component.scss']
})
export class BuildingsComponent implements OnInit {

    private buildings: Buildings;
    private userAccount: UserAccount;
    private currentlyUpgrading: string;
    private percent: number;
    private remainingTime: number;
    private remainingTimeString: string;
    private buildingEntities: ModelEntity[] = [];

    constructor(private _router: Router,
                private _accountService: AccountService,
                private _gameService: GameService) {
    }

    ngOnInit() {
        this.userAccount = this._accountService.userAccount;
        this.buildings = this.userAccount.buildings;
        this.currentlyUpgrading = this.userAccount.buildingQueue;
        this.buildingEntities.push(this._gameService.getModelEntity('Hall', this.userAccount));
        this.buildingEntities.push(this._gameService.getModelEntity('Barracks', this.userAccount));
        this.buildingEntities.push(this._gameService.getModelEntity('War', this.userAccount));
        this.buildingEntities.push(this._gameService.getModelEntity('Research', this.userAccount));
        this.buildingEntities.push(this._gameService.getModelEntity('Farm', this.userAccount));
        this.buildingEntities.push(this._gameService.getModelEntity('Vault', this.userAccount));
        this.buildingEntities.push(this._gameService.getModelEntity('StoneWarehouse', this.userAccount));
        this.buildingEntities.push(this._gameService.getModelEntity('WoodWarehouse', this.userAccount));
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
        const hours : number = Math.floor(this.remainingTime / 7200000);
        const minutes : number = Math.floor((this.remainingTime % 7200000) / 60000);
        const seconds : number = Math.floor(((this.remainingTime % 7200000) % 60000) / 1000);
        this.remainingTimeString = (hours < 10 ? "0" + hours : hours + "").concat(":").concat(minutes < 10 ? "0" + minutes : minutes + "").concat(":").concat(seconds < 10 ? "0" + seconds : seconds + "");
    }

    isUpgradePossible(modelEntity: ModelEntity): boolean {
        if(modelEntity.name == 'Hall')
            return true;
        return modelEntity.level < this.userAccount.buildings.hall;
    }
}
