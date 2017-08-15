import { Component, Input, OnInit } from '@angular/core';
import { Report } from './shared/_index';

@Component({
    selector: 'app-report',
    templateUrl: './report.component.html'
})
export class ReportComponent implements OnInit {

    @Input() report: Report;
    date: Date;
    image: string;

    ngOnInit() {
        this.date = new Date(this.report.timestamp);
        const imageName = this.report.image.substr(8, this.report.image.length - 1);
        this.image = `/api/report/uploads/${imageName}`;
    }

}
