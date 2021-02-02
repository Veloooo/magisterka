import {UserPopulation} from './user-population';
import {Resources} from './resources';
import {Buildings} from './buildings';
import {Defence} from './defence';
import {Research} from './research';
import {Units} from './units';
import {GameEvent} from './gameEvent';

export class UserAccount{
    fraction: string;
    nick: string;
    population: UserPopulation;
    resources: Resources;
    buildings: Buildings;
    research: Research;
    units: Units;
    defence: Defence;
    events: GameEvent[];
    gold: number;
    wood: number;
    stone: number;
    people: number;
    buildingQueue: string;
    buildingFinishTime: Date;
    buildingStartTime: Date;
    researchQueue: string;
    researchFinishTime: Date;
    researchStartTime: Date;
    woodProduction: number;
    stoneProduction: number;
    goldProduction: number;

    getLevel(what) : number {
        switch(what){
            case 'Hall' : return this.buildings.hall;
            case 'Barracks' : return this.buildings.barracks;
            case 'War' : return this.buildings.war;
            case 'Research' : return this.buildings.research;
            case 'Stone' : return this.buildings.stoneWarehouse;
            case 'Gold' : return this.buildings.vault;
            case 'Wood' : return this.buildings.woodWarehouse;
            case 'Farm' : return this.buildings.farm;
        };
    }
}
