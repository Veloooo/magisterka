import {Units} from './units';
import {Hero} from './hero';

export interface Mission {
    units: Units;
    hero: Hero;
    time: number;
    type: string;
    target: number;
    message: string;
    missionArrivalTime: Date;
    missionFinishTime: Date;
    missionReturnTime: Date;
}
