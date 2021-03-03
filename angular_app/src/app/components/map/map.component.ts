import { Component, OnInit } from '@angular/core';
import {UserAccount} from '../../model/user-account';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {GameService} from '../../core/game-service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {
  private account: UserAccount;
  private allAccounts: UserAccount[] = [];
  private isDataLoaded = false;

  constructor(private _router: Router,
              private _accountService: AccountService,
              private _gameService: GameService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    this._accountService.getAllUsers().subscribe(account => {
      let allAccounts : UserAccount[] = JSON.parse(JSON.stringify(account));
      this.isDataLoaded = true;
      let index = 0;
      for(let i = 1 ; i <= 16; i++) {
        if(index > allAccounts.length - 1 || allAccounts[index].cityPosition != i) {
          let userAccount = new UserAccount();
          userAccount.cityPosition = i;
          this.allAccounts.push(userAccount);
        }
        else {
          this.allAccounts.push(allAccounts[index]);
          index++;
        }
      }
    });
  }

  getImageName(position: number) : string{
    let row = Math.ceil(position/4);
    let column = (position - 4 * (row - 1));
    return '../../../assets/images/map/row-' + row + '-col-' + column + '.jpg';
  }

  getPositionCityImage(fraction: string){
      if(fraction)
          return "../../../assets/images/cities/" + fraction + ".jpg";
      else return "";
  }

  getUnitNameByFraction(fraction: string, id: number){
      return this._gameService.getUnitNameByFraction(fraction, id);
  }

  sendMission(position: number){
    this._gameService.missionTarget = position;
    this._router.navigate(['../mission'], {relativeTo: this.route});
  }
}
