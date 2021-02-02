import {ModelEntity} from './model-entity';

export interface ResourceEntity extends ModelEntity{
    workers : number;
    currentProduction : number;
}
