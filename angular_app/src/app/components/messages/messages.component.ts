import { Component, OnInit } from '@angular/core';
import {GameMessage} from '../../model/game-message';
import {AccountService} from '../../core/account.service';
import {GameService} from '../../core/game-service';
import {UserAccount} from '../../model/user-account';

@Component({
  selector: 'app-messages',
  templateUrl: './messages.component.html',
  styleUrls: ['./messages.component.scss']
})
export class MessagesComponent implements OnInit {

  messages: GameMessage[];
  userAccount: UserAccount;

  constructor(private _accountService: AccountService,
              private _gameService: GameService) { }

  ngOnInit() {
    this.userAccount = this._accountService.userAccount;
    this.messages = this.userAccount.messages;
  }

}
