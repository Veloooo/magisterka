import {Statistics} from './statistics';
import {Item} from './item';

export interface Hero {
    id: number;
    level: number;
    dungeonsCompleted: number;
    heroClass: string;
    items: Item[];
    statistics: Statistics;
    userId: string;
}
