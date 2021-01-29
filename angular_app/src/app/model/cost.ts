export class Cost {
    gold: number;
    stone: number;
    wood: number;
    minutes: number;
    name: string;

    constructor(gold: number, stone: number, wood: number, minutes: number, name: string) {
        this.gold = gold;
        this.stone = stone;
        this.wood = wood;
        this.minutes = minutes;
        this.name = name;
    }

}
