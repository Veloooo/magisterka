import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Resources} from '../../model/resources';
import {UserAction} from '../../model/user-action';
import {UserAccount} from '../../model/user-account';
import moment = require('moment');
import {GameService} from '../../core/game-service';
import {ResourceEntity} from '../../model/resource-entity';

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
        this.resources.sawmillWorkers = this.resourceEntities.find(resource => resource.name == 'Sawmill').workers;
        this.resources.stonepitWorkers = this.resourceEntities.find(resource => resource.name == 'Stonepit').workers;
        this.resources.goldmineWorkers = this.resourceEntities.find(resource => resource.name == 'Goldmine').workers;
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


    subtractWorker(resource: ResourceEntity) {
        if(resource.workers > 0) {
            resource.workers -= 1;
            this.resources.freeWorkers++;
        }
    }

    addWorker(resource: ResourceEntity) {
        if(this.resources.freeWorkers > 0) {
            resource.workers += 1;
            this.resources.freeWorkers--;
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
         this.remainingTimeString = this._gameService.getDateString(this.remainingTime);
    }

    isUpgradePossible(modelEntity: ResourceEntity): boolean {
        return modelEntity.level < this.userAccount.buildings.hall;
    }
}
