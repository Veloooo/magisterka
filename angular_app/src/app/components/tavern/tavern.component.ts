import {Component, OnInit} from '@angular/core';
import {UserAction} from '../../model/user-action';
import {ActivatedRoute, Router} from '@angular/router';
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
    private availableHeroes= [];
    private userAccount: UserAccount;

    constructor(private _router: Router,
                private _accountService: AccountService,
                private route: ActivatedRoute,
                private _gameService: GameService) {
    }

    ngOnInit() {
        this.userAccount = this._accountService.userAccount;
        this.heroes = this.userAccount.heroes;
        let availableHeroes = this._gameService.getAllAvailableHeroesByFraction(this.userAccount.fraction);
        availableHeroes.forEach(hero => {
            console.log(hero);
            if (this.heroes.find(h => h.heroClass == hero) == null) {
                this.availableHeroes.push(hero)
            }
        });
    }

    recruit(who: string) {
        let userAction = new UserAction();
        userAction.data = who;
        userAction.action = 1;
        this._accountService.heroAction(userAction).subscribe(
            response => {
                if (response.statusCode == 433) {
                    alert('Too many heroes! Upgrade hall to lvl ' + (Math.floor(this.userAccount.buildings.hall / 10) * 10 + 10) + ' to recruit more heroes!');
                } else if (response.statusCode == 200) {
                    location.reload();
                    alert('Ok');
                }
            },
            err => {
                alert(err);
            });
    }

    viewHero() {
        this._router.navigate(['../hero', 0], {relativeTo: this.route});
    }

    isRecruitPossible(): boolean {
        return this.heroes.length * 10 <= this.userAccount.buildings.hall;
    }
}
