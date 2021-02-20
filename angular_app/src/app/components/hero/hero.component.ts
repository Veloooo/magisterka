import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AccountService} from '../../core/account.service';
import {GameService} from '../../core/game-service';
import {Hero} from '../../model/hero';
import {UserAccount} from '../../model/user-account';
import {Statistics} from '../../model/statistics';
import {Item} from '../../model/item';
import {UserAction} from '../../model/user-action';

@Component({
    selector: 'app-hero',
    templateUrl: './hero.component.html',
    styleUrls: ['./hero.component.scss']
})
export class HeroComponent implements OnInit {

    private hero: Hero;
    private heroTmp: Hero;
    private userAccount: UserAccount;
    private itemStatistics: Statistics = {strength: 0, agility: 0, charisma: 0, intelligence: 0} as Statistics;
    private statistics = ['Strength', 'Agility', 'Intelligence', 'Charisma'];
    private parts = ['Head', 'Body', 'Legs', 'Boots'];

    constructor(private _router: Router,
                private _accountService: AccountService,
                private route: ActivatedRoute,
                private _gameService: GameService) {
    }

    ngOnInit() {
        this.userAccount = this._accountService.userAccount;
        this.hero = this.userAccount.heroes[0]; //.find(e => e.id.toString() == this.route.snapshot.paramMap.get("id"));
        this.heroTmp = JSON.parse(JSON.stringify(this.hero));
        this.calculateStatistics();
    }

    isButtonActive(button: string, operation: string) {
        if (operation == '+') {
            return this.heroTmp.statistics.skillPoints > 0;
        } else if (operation == '-') {
            switch (button) {
                case 'Strength': {
                    return this.heroTmp.statistics.strength > this.hero.statistics.strength;
                }
                case 'Intelligence': {
                    return this.heroTmp.statistics.intelligence > this.hero.statistics.intelligence;
                }
                case 'Agility': {
                    return this.heroTmp.statistics.agility > this.hero.statistics.agility;
                }
                case 'Charisma': {
                    return this.heroTmp.statistics.charisma > this.hero.statistics.charisma;
                }
            }
        }

        return true;
    }

    performStatisticsAction(button: string, operation: number) {
        this.heroTmp.statistics.skillPoints -= operation;
        switch (button) {
            case 'Strength': {
                this.heroTmp.statistics.strength += operation;
                break;
            }
            case 'Intelligence': {
                this.heroTmp.statistics.intelligence += operation;
                break;
            }
            case 'Agility': {
                this.heroTmp.statistics.agility += operation;
                break;
            }
            case 'Charisma': {
                this.heroTmp.statistics.charisma += operation;
                break;
            }
        }
    }

    calculateStatistics() {
        this.itemStatistics = {strength: 0, agility: 0, charisma: 0, intelligence: 0} as Statistics;
        this.heroTmp.items.filter(item => item.isWorn == 1).forEach(item => {
            this.itemStatistics.strength += item.statistics.strength;
            this.itemStatistics.agility += item.statistics.agility;
            this.itemStatistics.intelligence += item.statistics.intelligence;
            this.itemStatistics.charisma += item.statistics.charisma;
        })
    }

    getStatistics(statistic: string, overall: boolean) {
        switch (statistic) {
            case 'Strength': {
                return overall ? this.heroTmp.statistics.strength + this.itemStatistics.strength : this.heroTmp.statistics.strength;
            }
            case 'Intelligence': {
                return overall ? this.heroTmp.statistics.intelligence + this.itemStatistics.intelligence : this.heroTmp.statistics.intelligence;
            }
            case 'Agility': {
                return overall ? this.heroTmp.statistics.agility + this.itemStatistics.agility : this.heroTmp.statistics.agility;
            }
            case 'Charisma': {
                return overall ? this.heroTmp.statistics.charisma + this.itemStatistics.charisma : this.heroTmp.statistics.charisma;
            }
        }
    }

    getItemOfPart(part: string): Item {
        let item = this.heroTmp.items.find(item => item.part == part && item.isWorn == 1);
        if (item != null) {
            return item;
        } else {
            return this._gameService.getDummyItems().find(item => item.part == part);
        }
    }

    getItemFromBackpack(): Item[] {
        return this.heroTmp.items.filter(item => item.isWorn == 0);
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

    takeOff(item: Item) {
        item.isWorn = 0;
        this.calculateStatistics();
    }

    putOn(item: Item) {
        item.isWorn = 1;
        this.calculateStatistics();
    }

    getItemImage(item: Item): string {
        if (item.name.startsWith('no')) {
            return item.part + '.png';
        } else if (item.part == 'Legs') {
            return 'Legs.jpg';
        } else {
            return item.part + (Math.floor(item.levelRequired / 10) + 1) + ((item.part == 'Body') ? '.png' : '.jpg');
        }
    }

    isPartWorn(part: string): boolean {
        return this.heroTmp.items.find(item => item.part == part && item.isWorn == 1) != null;
    }
    backToTavern(){
        this._router.navigate(['../../../heroes'], {relativeTo: this.route});
    }

    saveHero(){
        let userAction = new UserAction();
        userAction.action = 2;
        userAction.data = JSON.stringify(this.heroTmp);
        this._accountService.heroAction(userAction).subscribe(
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
