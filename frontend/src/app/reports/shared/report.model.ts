import { timestamp } from 'rxjs/operator/timestamp';
export class Report {
    constructor(public image: string,
        public timestamp: number,
        public description: string) { }
}
