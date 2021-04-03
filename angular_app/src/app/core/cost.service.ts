import {Injectable} from '@angular/core';
import {Cost} from '../model/cost';

@Injectable()
export class CostService {

    costs : Cost[] = [];

    constructor(){
        this.costs.push(new Cost(175, 50, 20, 10, "Sawmill"));
        this.costs.push(new Cost(225, 10, 150, 10, "Goldmine"));
        this.costs.push(new Cost(200, 5, 180, 10, "Stonepit"));
        this.costs.push(new Cost(150, 100, 200, 9, "Agriculture"));
        this.costs.push(new Cost(100, 200, 250, 13, "Building"));
        this.costs.push(new Cost(120, 90, 150, 8, "Logistics"));
        this.costs.push(new Cost(140, 170, 220, 11, "Attack"));
        this.costs.push(new Cost(150, 190, 160, 11, "Armour"));
        this.costs.push(new Cost(240, 240, 240, 14, "Mining"));
        this.costs.push(new Cost(300, 300, 300, 20, "Hall"));
        this.costs.push(new Cost(140, 220, 200, 14, "Barracks"));
        this.costs.push(new Cost(170, 160, 170, 13, "War"));
        this.costs.push(new Cost(200, 120, 120, 12, "Research"));
        this.costs.push(new Cost(100, 200, 200, 8, "Farm"));
        this.costs.push(new Cost(200, 100, 100, 5, "Vault"));
        this.costs.push(new Cost(100, 200, 100, 5, "StoneWarehouse"));
        this.costs.push(new Cost(100, 100, 200, 5, "WoodWarehouse"));
        this.costs.push(new Cost(150, 300, 150, 10, "Wall"));
        this.costs.push(new Cost(150, 300, 150, 13, "GuardTowers"));
        this.costs.push(new Cost(220, 200, 240, 11, "Moat"));
    }

    costOfLevel(cost : Cost, level: number) : Cost{
        let costOfLevel = Object.assign({}, cost);
        costOfLevel.gold *= level;
        costOfLevel.wood *= level;
        costOfLevel.stone *= level;
        costOfLevel.minutes *= level;
        return costOfLevel;
    }

    getCostOfUnit(unit: number){
        return new Cost(100 * unit, 100 * unit, 100 * unit, 0, "");
    }
    isEnoughResources(cost: Cost, gold: number, stone: number, wood: number): boolean{
        return cost.gold < gold && cost.wood < wood && cost.stone < stone;
    }
}
