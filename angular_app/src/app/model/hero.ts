import {Statistics} from './statistics';
import {Item} from './item';

export interface Hero {
    id: number;
    level: number;
    heroClass: string;
    items: Item[];
    statistics: Statistics;
    userId: string;
}
