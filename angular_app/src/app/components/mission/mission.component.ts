import {Component, OnInit} from '@angular/core';
import {UserAccount} from '../../model/user-account';
import {GameService} from '../../core/game-service';
import {AccountService} from '../../core/account.service';
import {Hero} from '../../model/hero';
import {Units} from '../../model/units';
import {UserAction} from '../../model/user-action';

@Component({
    selector: 'app-mission',
    templateUrl: './mission.component.html',
    styleUrls: ['./mission.component.scss']
})
export class MissionComponent implements OnInit {
    private account: UserAccount;
    private heroes: Hero[];
    private mission: string;
    private selectedHero: Hero;
    private unitsMissionIndex: number[] = [1, 2, 3, 4, 5, 6];
    private unitsMission: Units = new Units();
    private hours = 1;
    private isMissionPossible: boolean;
    private message = 'No free heroes available!';
    private unitCount = 0;
    private target: number;

    constructor(private _accountService: AccountService,
                private _gameService: GameService) {
    }

    ngOnInit() {
        this.account = this._accountService.userAccount;
        this.target = this._gameService.missionTarget;
        let heroesDuringMission: Hero [] = [];
        this.account.missions.forEach(mission => heroesDuringMission.push(mission.hero));
        this.heroes = this.account.heroes.filter(hero =>  heroesDuringMission.find(heroMission => hero.id == heroMission.id) == null);
        this.isMissionPossible = this.heroes.length > 0;
        if(this._gameService.heroDungeon != null){
            this.selectedHero = this._gameService.heroDungeon;
        }
        console.log(this.target);
    }

    getUnitNameByFraction(fraction: string, id: number) {
        return this._gameService.getUnitNameByFraction(fraction, id);
    }

    getAvailableMissions(target: string): string[] {
        if (target == 'Dungeons') {
            return ['Attack'];
        } else {
            return ['Attack', 'Station'];
        }
    }

    getUnitCountById(id: number, units: Units) {
        switch (id) {
            case 1:
                return units.unit1;
            case 2:
                return units.unit2;
            case 3:
                return units.unit3;
            case 4:
                return units.unit4;
            case 5:
                return units.unit5;
            case 6:
                return units.unit6;
        }
    }

    changeUnit(id: number, action: number) {
        switch (id) {
            case 1 :
                this.unitsMission.unit1 += action;
                break;
            case 2 :
                this.unitsMission.unit2 += action;
                break;
            case 3 :
                this.unitsMission.unit3 += action;
                break;
            case 4 :
                this.unitsMission.unit4 += action;
                break;
            case 5 :
                this.unitsMission.unit5 += action;
                break;
            case 6 :
                this.unitsMission.unit6 += action;
                break;
        }
        this.unitCount+=action;
    }

    changeTime(action: number) {
        this.hours += action;
    }

    isMissionReady(){
        if(this.unitCount < 1 || this.selectedHero == null || this.mission == null)
            return false;

        return true;
    }

    sendMission() {
        let mission = {
            hero: this.selectedHero,
            target: this.target,
            type: this.mission,
            time: this.mission == 'Station' ? this.hours : 0,
            units: this.unitsMission
        };
        let userAction: UserAction = new UserAction();
        userAction.data = JSON.stringify(mission);
        this._accountService.missionAction(userAction).subscribe(
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
}
