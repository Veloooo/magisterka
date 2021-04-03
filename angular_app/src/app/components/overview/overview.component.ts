import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {UserAccount} from '../../model/user-account';
import moment = require('moment');
import {GameService} from '../../core/game-service';
import {Mission} from '../../model/mission';

@Component({
    selector: 'app-overview',
    templateUrl: './overview.component.html',
    styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {

    private account: UserAccount;

    constructor(private _router: Router,
                private _accountService: AccountService,
                private _gameService: GameService) {

        setInterval(() => {
            this.updateMissionsMessages();
        }, 1);
    }

    ngOnInit() {
        this.account = this._accountService.userAccount;
    }

    updateMissionsMessages() {
        this.account.missions.forEach(m => m.message = this.getMissionMessage(m));
    }

    getCityCoords() {
        let row = Math.ceil(this.account.cityPosition / 4);
        let column = (this.account.cityPosition - 4 * (row - 1));
        return row + ':' + column;
    }

    getUnitNameByFraction(fraction: string, id: number) {
        return this._gameService.getUnitNameByFraction(fraction, id);
    }

    getMissionMessage(mission: Mission) {
        let message = '';
        let arrivalTimeString = this.getRemainingTimeString(mission.missionArrivalTime);
        if(moment(mission.missionReturnTime).diff(moment()) > 0) {
            if (moment(mission.missionArrivalTime).diff(moment()) > 0) {
                message = message.concat('Your mission: ' + mission.type + ' will arrive target ' + this.getMissionTargetString(mission.target) + ' in: ' + arrivalTimeString);
            }
            if (mission.type == 'Station' && moment(mission.missionFinishTime).diff(moment()) > 0) {
                if (moment(mission.missionArrivalTime).diff(moment()) < 0) {
                    message = message.concat('Your mission: ' + mission.type);
                }
                message = message.concat(' will stay at ' + this.getMissionTargetString(mission.target) + ' for : ' + this.getRemainingTimeString(mission.missionFinishTime));
            }
            if (moment(mission.missionArrivalTime).diff(moment()) < 0 && moment(mission.missionFinishTime).diff(moment()) < 0) {
                message = message.concat('Your mission: ' + mission.type + ' from ' + this.getMissionTargetString(mission.target));
            } else {
                message = message.concat(' and');
            }
            message = message.concat(' will return to your city in ' + this.getRemainingTimeString(mission.missionReturnTime));
        }
        return message;
    }

    getRemainingTimeString(date: Date) {
        return moment.utc(moment.duration(moment(date).diff(moment(), 'seconds'), 'seconds').asMilliseconds()).format('HH:mm:ss');
    }

    getMissionTargetString(target: number) {
        if (target == 0) {
            return 'Dungeons';
        } else {
            let row = Math.ceil(target / 4);
            let column = (target - 4 * (row - 1));
            return ' (' + row + ',' + column + ') ';
        }
    }
}
