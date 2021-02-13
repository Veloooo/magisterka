import { Component, OnInit } from '@angular/core';
import {UserAction} from '../../model/user-action';
import {Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {GameService} from '../../core/game-service';
import {Hero} from '../../model/hero';
import {UserAccount} from '../../model/user-account';

@Component({
  selector: 'app-tavern',
  templateUrl: './tavern.component.html',
  styleUrls: ['./tavern.component.scss']
})
export class TavernComponent implements OnInit {

  private heroes: Hero[];
  private userAccount: UserAccount;
  constructor(private _router: Router,
              private _accountService: AccountService,
              private _gameService: GameService) { }

  ngOnInit() {
    this.userAccount = this._accountService.userAccount;
    this.heroes = this.userAccount.heroes;
  }

  recruit(who: string){
    let userAction = new UserAction();
    userAction.data = who;
    userAction.action = 1;
    this._accountService.heroAction(userAction).subscribe(
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
