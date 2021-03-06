import {Item} from './item';
import {DungeonUnit} from './dungeon-unit';

export interface Dungeon {
    id: number;
    level: number;
    reward: Item;
    dungeonUnits: DungeonUnit[];
    completed: number;
}
