import {UserPopulation} from './user-population';
import {Resources} from './resources';
import {Buildings} from './buildings';
import {Defence} from './defence';
import {Research} from './research';
import {Units} from './units';
import {GameEvent} from './gameEvent';
import {Hero} from './hero';
import {PlayerStatistics} from './player-statistics';

export class UserAccount{
    fraction: string;
    nick: string;
    population: UserPopulation;
    resources: Resources;
    buildings: Buildings;
    research: Research;
    units: Units;
    defence: Defence;
    playerStatistics: PlayerStatistics;
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
    heroes: Hero[];
}
