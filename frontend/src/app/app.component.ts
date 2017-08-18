import { Component } from '@angular/core';
import { Report, ReportService } from './reports/shared/_index';
import * as EventBus from 'vertx3-eventbus-client';

declare var Materialize: any;
//declare var EventBus: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  data: Report[] = [];
  loading = true;
  bus;

  constructor(public service: ReportService) {
    this.bus = new EventBus(window.location.protocol + '//' + window.location.host + '/eventbus');
    this.bus.onopen = () => {
      this.bus.registerHandler('report.added', (err, message) => {
        this.data.unshift(message.body);
      });
    };
    service.all().subscribe(res => {
      this.data = res;
      this.loading = false;
    }, err => Materialize.toast('Error al cargar Lotes', 4000));
  }

}
