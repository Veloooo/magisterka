import {Component, OnInit} from '@angular/core';
import {Buildings} from '../../model/buildings';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {Units} from '../../model/units';
import {UnitEntity} from '../../model/unit-entity';
import {GameService} from '../../core/game-service';
import {UserAccount} from '../../model/user-account';
import {CostService} from '../../core/cost.service';
import {Cost} from '../../model/cost';
import {UserAction} from '../../model/user-action';

@Component({
    selector: 'app-barracks',
    templateUrl: './barracks.component.html',
    styleUrls: ['./barracks.component.scss']
})
export class BarracksComponent implements OnInit {

    private units: UnitEntity[];
    private account: UserAccount;


    constructor(private _router: Router,
                private _accountService: AccountService,
                private _gameService: GameService,
                private _costService: CostService) {
    }

    ngOnInit() {
        this.units = this._accountService.userAccount.units;
        this.account = this._accountService.userAccount;
        this.units = [];
        this.units.push({
            amount: this.account.units.unit1,
            name: this._gameService.getUnitNameByFraction(this.account.fraction, 1),
            basicCost: this._costService.getCostOfUnit(1),
            currentCost: this._costService.getCostOfUnit(0),
            recruit: 0
        });
        this.units.push({
            amount: this.account.units.unit2,
            name: this._gameService.getUnitNameByFraction(this.account.fraction, 2),
            basicCost: this._costService.getCostOfUnit(2),
            currentCost: this._costService.getCostOfUnit(0),
            recruit: 0
        });
        this.units.push({
            amount: this.account.units.unit3,
            name: this._gameService.getUnitNameByFraction(this.account.fraction, 3),
            basicCost: this._costService.getCostOfUnit(3),
            currentCost: this._costService.getCostOfUnit(0),
            recruit: 0
        });
        this.units.push({
            amount: this.account.units.unit4,
            name: this._gameService.getUnitNameByFraction(this.account.fraction, 4),
            basicCost: this._costService.getCostOfUnit(4),
            currentCost: this._costService.getCostOfUnit(0),
            recruit: 0
        });
        this.units.push({
            amount: this.account.units.unit5,
            name: this._gameService.getUnitNameByFraction(this.account.fraction, 5),
            basicCost: this._costService.getCostOfUnit(5),
            currentCost: this._costService.getCostOfUnit(0),
            recruit: 0
        });
        this.units.push({
            amount: this.account.units.unit6,
            name: this._gameService.getUnitNameByFraction(this.account.fraction, 6),
            basicCost: this._costService.getCostOfUnit(6),
            currentCost: this._costService.getCostOfUnit(0),
            recruit: 0
        });
    }

    setRecruit(entity: UnitEntity, number: number) {
        entity.recruit += number;
        entity.currentCost.wood = entity.basicCost.wood * entity.recruit;
        entity.currentCost.gold = entity.basicCost.gold * entity.recruit;
        entity.currentCost.stone = entity.basicCost.stone * entity.recruit;
    }

    areEnoughResources(nextCost: Cost, cost: Cost) {
        return nextCost.gold + cost.gold > this.account.gold || nextCost.stone + cost.stone > this.account.stone || nextCost.wood + cost.wood > this.account.wood;
    }

    recruit(unit: number, howMany: number){
        let userAction = new UserAction();
        userAction.action = unit;
        userAction.data = howMany.toString();
        console.log(howMany);
        this._accountService.barracksAction(userAction).subscribe(
            response => {
                if (response.statusCode == 200) {
                    location.reload();
                    alert('Ok');
                }
            },
            err => {
                alert(err);
            });
    }
}
