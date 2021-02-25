import { Component, OnInit } from '@angular/core';
import {UserAccount} from '../../model/user-account';
import {GameService} from '../../core/game-service';
import {AccountService} from '../../core/account.service';
import {Hero} from '../../model/hero';
import {FormControl, FormGroup} from '@angular/forms';
import {Units} from '../../model/units';

@Component({
  selector: 'app-mission',
  templateUrl: './mission.component.html',
  styleUrls: ['./mission.component.scss']
})
export class MissionComponent implements OnInit {
  private account: UserAccount;
  private heroes: Hero[];
  private mission: string;
  private selectedHero= new FormGroup({
    hero: new FormControl(''),
  });
  private unitsMissionIndex: number[] = [1,2,3,4,5,6];
  private unitsMission: Units = new Units();



  constructor(private _accountService: AccountService,
              private _gameService: GameService) { }

  ngOnInit() {
    this.account = this._accountService.userAccount;
    this.heroes = this.account.heroes;
  }

  getUnitNameByFraction(fraction: string, id: number){
    return this._gameService.getUnitNameByFraction(fraction, id);
  }

  getAvailableMissions(target: string) : string[] {
    console.log(target);
    if(target == 'Dungeons')
      return ['Attack'];
    else return ['Attack', 'Station'];
  }

  isStationSelected() : boolean {
    return this.mission == 'Station';
  }

  getUnitCountById(id: number, units: Units){
    switch(id){
      case 1: return units.unit1;
      case 2: return units.unit2;
      case 3: return units.unit3;
      case 4: return units.unit4;
      case 5: return units.unit5;
      case 6: return units.unit6;
    }
  }

  getUnitMissionCountById(id: number){
    switch(id){
      case 1: return this.account.units.unit1;
      case 2: return this.account.units.unit2;
      case 3: return this.account.units.unit3;
      case 4: return this.account.units.unit4;
      case 5: return this.account.units.unit5;
      case 6: return this.account.units.unit6;
    }
  }

  changeUnit(id: number, action: number) {
    switch(id){
      case 1 : this.unitsMission.unit1 += action; break;
      case 2 : this.unitsMission.unit2 += action; break;
      case 3 : this.unitsMission.unit3 += action; break;
      case 4 : this.unitsMission.unit4 += action; break;
      case 5 : this.unitsMission.unit5 += action; break;
      case 6 : this.unitsMission.unit6 += action; break;
    }
  }
}
