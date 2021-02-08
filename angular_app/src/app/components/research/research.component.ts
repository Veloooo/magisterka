import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Research} from '../../model/research';
import {ModelEntity} from '../../model/model-entity';
import {CostService} from '../../core/cost.service';
import {UserAction} from '../../model/user-action';
import {UserAccount} from '../../model/user-account';
import moment = require('moment');
import {ResearchEntity} from '../../model/research-entity';
import {GameService} from '../../core/game-service';

@Component({
    selector: 'app-research',
    templateUrl: './research.component.html',
    styleUrls: ['./research.component.scss']
})
export class ResearchComponent implements OnInit {

    private research: Research;
    private researchEntities: ResearchEntity[] = [];
    private currentlyUpgrading: string;
    private userAccount: UserAccount;
    private percent: number;
    private remainingTime: number;
    private remainingTimeString: string;

    constructor(private _router: Router,
                private _accountService: AccountService,
                private _gameService: GameService) {
    }

    ngOnInit() {
        this.userAccount = this._accountService.userAccount;
        this.research = this.userAccount.research;
        this.currentlyUpgrading = this.userAccount.researchQueue;
        this.researchEntities.push((this._gameService.getResearchEntity('Building', this.userAccount)));
        this.researchEntities.push((this._gameService.getResearchEntity('Agriculture', this.userAccount)));
        this.researchEntities.push((this._gameService.getResearchEntity('Mining', this.userAccount)));
        this.researchEntities.push((this._gameService.getResearchEntity('Logistics', this.userAccount)));
        this.researchEntities.push((this._gameService.getResearchEntity('Attack', this.userAccount)));
        this.researchEntities.push((this._gameService.getResearchEntity('Armour', this.userAccount)));
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
            if(this.percent > 100)
                this.percent = 100;
            this.remainingTime = moment(this.userAccount.researchFinishTime).diff(moment());
            if (this.remainingTime >= 0)
                this.setDateString();
            else this.remainingTimeString = "Finalizing upgrade..."
        }
    }

    setDateString() {
        const hours: number = Math.floor(this.remainingTime / 7200000);
        const minutes: number = Math.floor((this.remainingTime % 7200000) / 60000);
        const seconds: number = Math.floor(((this.remainingTime % 7200000) % 60000) / 1000);
        this.remainingTimeString = (hours < 10 ? '0' + hours : hours + '').concat(':').concat(minutes < 10 ? '0' + minutes : minutes + '').concat(':').concat(seconds < 10 ? "0" + seconds : seconds + "");
    }

    isUpgradePossible(modelEntity: ResearchEntity): boolean {
        if(modelEntity.institute == 'War')
            return modelEntity.level < this.userAccount.buildings.war;
        else
            return modelEntity.level < this.userAccount.buildings.research;
    }
}
