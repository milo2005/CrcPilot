import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Report } from './report.model';
import 'rxjs/add/operator/map';

@Injectable()
export class ReportService {

    constructor(public http: HttpClient) { }

    all() {
        return this.http.get<ResponseReport>('/api/report')
            .map(res => res.data);
    }

}

class ResponseReport {
    constructor(public success: boolean,
        public data: Report[]) { }
}
