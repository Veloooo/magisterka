import {Statistics} from './statistics';
import {Item} from './item';
import {Dungeon} from './dungeon';

export interface Hero {
    id: number;
    level: number;
    dungeonsCompleted: number;
    heroClass: string;
    items: Item[];
    dungeons: Dungeon[];
    statistics: Statistics;
    userId: string;
    nextDungeon: Dungeon;
}
