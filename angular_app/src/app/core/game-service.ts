import {Injectable} from '@angular/core';
import {CostService} from './cost.service';
import {UserAccount} from '../model/user-account';

@Injectable()
export class GameService {

    constructor(private _costService : CostService){

    }

    getResourceEntity(what: string, user: UserAccount){
        let level = 1;
        let production = 1;
        let workers = 1;
        switch(what){
            case 'Goldmine' : production = user.goldProduction; workers = user.resources.goldmineWorkers; level = user.resources.goldmineLvl; break;
            case 'Stonepit' : production = user.stoneProduction; workers = user.resources.stonepitWorkers; level = user.resources.stonepitLvl; break;
            case 'Sawmill' :  production = user.woodProduction; workers = user.resources.sawmillWorkers; level = user.resources.sawmillLvl; break;
        }
        return {
            level: level,
            name: what,
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == what), level),
            workers: workers,
            currentProduction: production
        }
    }

    getModelEntity (what: string, user: UserAccount){
        let level = 1;
        switch(what){
            case 'Hall' : level = user.buildings.hall; break;
            case 'Barracks' :  level = user.buildings.barracks; break;
            case 'War' :  level = user.buildings.war; break;
            case 'Research' :  level = user.buildings.research; break;
            case 'StoneWarehouse' :  level = user.buildings.stoneWarehouse; break;
            case 'Vault' :  level = user.buildings.vault; break;
            case 'WoodWarehouse' :  level = user.buildings.woodWarehouse; break;
            case 'Farm' :  level = user.buildings.farm; break;
        };

        return {
            level: level,
            name: what,
            currentCost: this._costService.costOfLevel(this._costService.costs.find(cost => cost.name == what), level)
        }
    }
}
