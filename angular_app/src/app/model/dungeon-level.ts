import {Item} from './item';
import {DungeonUnit} from './dungeon-unit';

export interface DungeonLevel {
    id: number;
    level: number;
    reward: Item;
    guardians: DungeonUnit[];
}
