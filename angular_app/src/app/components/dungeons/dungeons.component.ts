import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {GameService} from '../../core/game-service';
import {UserAccount} from '../../model/user-account';
import {Hero} from '../../model/hero';

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
  }

  sendMission(hero: Hero){
    this._gameService.missionTarget = 0;
    this._gameService.heroDungeon = hero;
    this._router.navigate(['../mission'], {relativeTo: this.route});
  }
}
