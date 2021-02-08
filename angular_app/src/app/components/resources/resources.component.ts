import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Resources} from '../../model/resources';
import {UserAction} from '../../model/user-action';
import {Cost} from '../../model/cost';
import {CostService} from '../../core/cost.service';
import {UserAccount} from '../../model/user-account';
import moment = require('moment');
import {GameService} from '../../core/game-service';
import {ResourceEntity} from '../../model/resource-entity';
import {ResearchEntity} from '../../model/research-entity';

@Component({
    selector: 'app-resources',
    templateUrl: './resources.component.html',
    styleUrls: ['./resources.component.scss']
})
export class ResourcesComponent implements OnInit {
    private resources: Resources;
    private userAccount: UserAccount;
    private resourceEntities: ResourceEntity[] = [];
    private percent: number;
    private remainingTime: number;
    private remainingTimeString: string;
    private currentlyUpgrading: string;
    private stoneProduction: number;
    private woodProduction: number;
    private goldProduction: number;


    constructor(private _router: Router,
                private _accountService: AccountService,
                private _gameService: GameService) {
    }

    ngOnInit() {
        this.userAccount = this._accountService.userAccount;
        this.resources = this.userAccount.resources;
        this.resourceEntities.push((this._gameService.getResourceEntity('Sawmill', this.userAccount)));
        this.resourceEntities.push((this._gameService.getResourceEntity('Goldmine', this.userAccount)));
        this.resourceEntities.push((this._gameService.getResourceEntity('Stonepit', this.userAccount)));
        this.currentlyUpgrading = this.userAccount.buildingQueue;
        this.stoneProduction = this.userAccount.stoneProduction;
        this.woodProduction= this.userAccount.woodProduction;
        this.goldProduction = this.userAccount.goldProduction;
        setInterval(() => {
            this.updateQueue();
        }, 1000);
    }

    submitWorkers() {
        let userAction = new UserAction();
        userAction.action = 3;
        userAction.data = JSON.stringify(this.resources);
        this._accountService.resourcesAction(userAction).subscribe(
            response => {
                if (response.statusCode != 200) {
                    alert('Error on saving resources!');
                }
                location.reload();
            },
            err => {
                alert(err);
            });
    }

    upgrade(what: string) {
        let userAction = new UserAction();
        if (this.currentlyUpgrading != null) {
            userAction.action = 2;
        } else {
            userAction.action = 1;
        }
        userAction.data = what;
        this._accountService.resourcesAction(userAction).subscribe(
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

    updateQueue() {
        if (this.userAccount.buildingQueue) {
            this.percent = Math.floor((new Date().getTime() - new Date(this.userAccount.buildingStartTime).getTime()) / (new Date(this.userAccount.buildingFinishTime).getTime()
                - new Date(this.userAccount.buildingStartTime).getTime()) * 100);
            this.remainingTime = moment(this.userAccount.buildingFinishTime).diff(moment());
            if(this.remainingTime >= 0)
                this.setDateString();
        }
    }

    setDateString() {
        const hours : number = Math.floor(this.remainingTime / 7200000);
        const minutes : number = Math.floor((this.remainingTime % 7200000) / 60000);
        const seconds : number = Math.floor(((this.remainingTime % 7200000) % 60000) / 1000);
        this.remainingTimeString = (hours < 10 ? "0" + hours : hours + "").concat(":").concat(minutes < 10 ? "0" + minutes : minutes + "").concat(":").concat(seconds < 10 ? "0" + seconds : seconds + "");
    }

    isUpgradePossible(modelEntity: ResourceEntity): boolean {
        return modelEntity.level < this.userAccount.buildings.hall;
    }
}
