import {Statistics} from './statistics';
import {Item} from './item';

export interface Hero {
    level: number;
    heroClass: string;
    items: Item[];
    statistics: Statistics;
}
