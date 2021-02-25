import {Injectable} from '@angular/core';
import {CostService} from './cost.service';
import {UserAccount} from '../model/user-account';
import {ResearchEntity} from '../model/research-entity';
import {Item} from '../model/item';
import {Statistics} from '../model/statistics';

@Injectable()
export class GameService {

    private unitNames;

    constructor(private _costService: CostService) {
        this.setUnitNames();
    }

    setUnitNames(){
        this.unitNames = new Map();
        let humanUnits = new Map();
        humanUnits.set(1, "Axeman");
        humanUnits.set(2, "Knight");
        humanUnits.set(3, "Archer");
        humanUnits.set(4, "Paladin");
        humanUnits.set(5, "GriphonRider");
        humanUnits.set(6, "Cavalry");
        this.unitNames.set("Human", humanUnits);
        this.unitNames.set("Demon", humanUnits);
        this.unitNames.set("Druid", humanUnits);
        this.unitNames.set("Orc", humanUnits);
    }

    getUnitNameByFraction(fraction: string, name: number){
        return this.unitNames.get(fraction).get(name);
    }



    getAllAvailableHeroesByFraction(fraction: string): string[] {
        let heroes = [];
        switch (fraction) {
            case 'Orc':
                heroes.push('Crusader');
                heroes.push('Assassin');
                heroes.push('Wizard');
                heroes.push('Paladin');
                return heroes;
            case 'Demon':
                heroes.push('Crusader');
                heroes.push('Assassin');
                heroes.push('Wizard');
                heroes.push('Paladin');
                return heroes;
            case 'Druid':
                heroes.push('Crusader');
                heroes.push('Assassin');
                heroes.push('Wizard');
                heroes.push('Paladin');
                return heroes;
            case 'Human': {
                heroes.push('Crusader');
                heroes.push('Assassin');
                heroes.push('Wizard');
                heroes.push('Paladin');
                return heroes;
            }
        }
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

    getDummyItems() : Item[]{
        let zeroStatistics: Statistics = { strength: 0, agility: 0, charisma: 0 , intelligence: 0} as Statistics;
        let itemsDummy: Item[] = [];
        itemsDummy.push({id:0, name: "no hat", isWorn : 1, statistics: zeroStatistics, levelRequired: 1, part: "Head", heroId: 0});
        itemsDummy.push({id:0, name: "no armour", isWorn : 1, statistics: zeroStatistics, levelRequired: 1,  part: "Body", heroId: 0});
        itemsDummy.push({id:0, name: "no legs armour", isWorn : 1, statistics: zeroStatistics, levelRequired: 1, part: "Legs", heroId: 0});
        itemsDummy.push({id:0, name: "no boots", isWorn : 1, statistics: zeroStatistics, levelRequired: 1,  part: "Boots", heroId: 0});
        return itemsDummy;
    }
}
