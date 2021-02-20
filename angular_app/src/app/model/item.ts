import {Statistics} from './statistics';

export class Item {
    id: number;
    name: string;
    part: string;
    levelRequired: number;
    statistics: Statistics;
    isWorn: number;
    heroId: number;
}
