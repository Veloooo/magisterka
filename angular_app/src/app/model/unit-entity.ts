import {Cost} from './cost';

export interface UnitEntity {
    amount: number;
    name: string;
    recruit: number;
    basicCost: Cost;
    currentCost: Cost;

}
