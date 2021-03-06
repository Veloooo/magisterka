import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {GameService} from '../../core/game-service';
import {UserAccount} from '../../model/user-account';
import {Hero} from '../../model/hero';
import {Item} from '../../model/item';

@Component({
  selector: 'app-dungeons',
  templateUrl: './dungeons.component.html',
  styleUrls: ['./dungeons.component.scss']
})
export class DungeonsComponent implements OnInit {

  private userAccount: UserAccount;
  private heroes: Hero[];
  private statistics = ['Strength', 'Agility', 'Intelligence', 'Charisma'];

  constructor(private _router: Router,
              private _accountService: AccountService,
              private _gameService: GameService,
              private route: ActivatedRoute) {
  }
  ngOnInit() {
    this.userAccount = this._accountService.userAccount;
    this.heroes = this.userAccount.heroes;
    this.heroes.forEach(this._gameService.getCurrentDungeon);
  }

  sendMission(hero: Hero){
    this._gameService.missionTarget = 0;
    this._gameService.heroDungeon = hero;
    this._router.navigate(['../mission'], {relativeTo: this.route});
  }

  getStatOfItem(stat: string, item: Item) {
    switch (stat) {
      case 'Strength': {
        return item.statistics.strength;
      }
      case 'Intelligence': {
        return item.statistics.intelligence;
      }
      case 'Agility': {
        return item.statistics.agility;
      }
      case 'Charisma': {
        return item.statistics.charisma;
      }
    }
  }
}
