import {Injectable} from '@angular/core';
import {CostService} from './cost.service';
import {UserAccount} from '../model/user-account';
import {ResearchEntity} from '../model/research-entity';
import {Item} from '../model/item';
import {Statistics} from '../model/statistics';
import {Hero} from '../model/hero';
import {Cost} from '../model/cost';

@Injectable()
export class GameService {

    private unitNames;
    public missionTarget;
    public heroDungeon: Hero;

    constructor(private _costService: CostService) {
        this.setUnitNames();
    }

    setUnitNames() {
        this.unitNames = new Map();
        let humanUnits = new Map();
        humanUnits.set(1, 'Axeman');
        humanUnits.set(2, 'Knight');
        humanUnits.set(3, 'Archer');
        humanUnits.set(4, 'Paladin');
        humanUnits.set(5, 'GriphonRider');
        humanUnits.set(6, 'Cavalry');
        this.unitNames.set('Human', humanUnits);
        let demonUnits = new Map();
        demonUnits.set(1, 'GoblinMage');
        demonUnits.set(2, 'Lizardman');
        demonUnits.set(3, 'Lifestealer');
        demonUnits.set(4, 'Behemoth');
        demonUnits.set(5, 'Banshee');
        demonUnits.set(6, 'Devil');
        this.unitNames.set('Demon', demonUnits);
        this.unitNames.set('Druid', humanUnits);
        this.unitNames.set('Orc', humanUnits);
    }

    getUnitNameByFraction(fraction: string, name: number) {
        return this.unitNames.get(fraction).get(name);
    }


    getAllAvailableHeroesByFraction(fraction: string): string[] {
        let heroes = [];
        switch (fraction) {
            case 'Orc':
                heroes.push('Warrior');
                heroes.push('Assassin');
                heroes.push('Wizard');
                heroes.push('Paladin');
                return heroes;
            case 'Demon':
                heroes.push('DarkKnight');
                heroes.push('DemonicAssassin');
                heroes.push('Sorcerer');
                heroes.push('Anubis');
                return heroes;
            case 'Druid':
                heroes.push('Warrior');
                heroes.push('Assassin');
                heroes.push('Wizard');
                heroes.push('Paladin');
                return heroes;
            case 'Human': {
                heroes.push('Warrior');
                heroes.push('Assassin');
                heroes.push('Wizard');
                heroes.push('Paladin');
                return heroes;
            }
        }
    }

    isUpgradePossible(cost: Cost, gold: number, stone: number, wood: number): boolean {
        return this._costService.isEnoughResources(cost, gold, stone, wood);
    }

    getResearchEntity(what: string, user: UserAccount): ResearchEntity {
        let level = 1;
        let institute;
        switch (what) {
            case 'Building' :
                level = user.research.building;
                institute = 'Research';
                break;
            case 'Agriculture' :
                level = user.research.agriculture;
                institute = 'Research';
                break;
            case 'Logistics' :
                level = user.research.logistics;
                institute = 'War';
                break;
            case 'Attack' :
                level = user.research.attack;
                institute = 'War';
                break;
            case 'Armour' :
                level = user.research.armour;
                institute = 'War';
                break;
            case 'Mining' :
                level = user.research.mining;
                institute = 'Research';
                break;
        }
        return {
            level: level,
            name: what,
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == what), level),
            institute: institute
        }
    }

    getResourceEntity(what: string, user: UserAccount) {
        let level = 1;
        let production = 1;
        let workers = 1;
        switch (what) {
            case 'Goldmine' :
                production = user.goldProduction;
                workers = user.resources.goldmineWorkers;
                level = user.resources.goldmineLvl;
                break;
            case 'Stonepit' :
                production = user.stoneProduction;
                workers = user.resources.stonepitWorkers;
                level = user.resources.stonepitLvl;
                break;
            case 'Sawmill' :
                production = user.woodProduction;
                workers = user.resources.sawmillWorkers;
                level = user.resources.sawmillLvl;
                break;
        }
        return {
            level: level,
            name: what,
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == what), level),
            workers: workers,
            currentProduction: production
        }
    }

    getModelEntity(what: string, user: UserAccount) {
        let level = 1;
        switch (what) {
            case 'Hall' :
                level = user.buildings.hall;
                break;
            case 'Barracks' :
                level = user.buildings.barracks;
                break;
            case 'War' :
                level = user.buildings.war;
                break;
            case 'Research' :
                level = user.buildings.research;
                break;
            case 'StoneWarehouse' :
                level = user.buildings.stoneWarehouse;
                break;
            case 'Vault' :
                level = user.buildings.vault;
                break;
            case 'WoodWarehouse' :
                level = user.buildings.woodWarehouse;
                break;
            case 'Farm' :
                level = user.buildings.farm;
                break;
            case 'Wall' :
                level = user.defence.cityWall;
                break;
            case 'GuardTowers' :
                level = user.defence.guardTowers;
                break;
            case 'Moat' :
                level = user.defence.moat;
                break;
        }
        ;

        return {
            level: level,
            name: what,
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == what), level)
        }
    }

    getDummyItems(): Item[] {
        let zeroStatistics: Statistics = {strength: 0, agility: 0, charisma: 0, intelligence: 0} as Statistics;
        let itemsDummy: Item[] = [];
        itemsDummy.push({id: 0, name: 'no hat', isWorn: 1, statistics: zeroStatistics, levelRequired: 1, part: 'Head', heroId: 0});
        itemsDummy.push({id: 0, name: 'no armour', isWorn: 1, statistics: zeroStatistics, levelRequired: 1, part: 'Body', heroId: 0});
        itemsDummy.push({id: 0, name: 'no legs armour', isWorn: 1, statistics: zeroStatistics, levelRequired: 1, part: 'Legs', heroId: 0});
        itemsDummy.push({id: 0, name: 'no boots', isWorn: 1, statistics: zeroStatistics, levelRequired: 1, part: 'Boots', heroId: 0});
        return itemsDummy;
    }

    getCurrentDungeon(hero: Hero){
        hero.nextDungeon = hero.dungeons.find(dungeon => dungeon.completed == 0);
    }

    getDateString(remainingTime: number) : string{
        const hours : number = Math.floor(remainingTime / 7200000);
        const minutes : number = Math.floor((remainingTime % 7200000) / 60000);
        const seconds : number = Math.floor(((remainingTime % 7200000) % 60000) / 1000);
        return (hours < 10 ? "0" + hours : hours + "").concat(":").concat(minutes < 10 ? "0" + minutes : minutes + "").concat(":").concat(seconds < 10 ? "0" + seconds : seconds + "");
    }
}
