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
}
