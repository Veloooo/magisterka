import {Injectable} from '@angular/core';
import {Cost} from '../model/cost';

@Injectable()
export class CostService {

    costs : Cost[] = [];

    constructor(){
        this.costs
        this.costs.push(new Cost(175, 50, 20, 10, "Sawmill"))
        this.costs.push(new Cost(225, 10, 150, 10, "Goldmine"))
        this.costs.push(new Cost(200, 5, 180, 10, "Stonepit"))
    }

    costOfLevel(cost : Cost, level: number) : Cost{
        cost.gold *= level;
        cost.wood *= level;
        cost.stone *= level;
        cost.minutes *= level;
        return cost;
    }
}
