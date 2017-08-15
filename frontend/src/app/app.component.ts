import { Component } from '@angular/core';
import { Report, ReportService } from './reports/shared/_index';

declare var Materialize: any;

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  data: Report[] = [];
  loading = true;

  constructor(public service: ReportService) {
    service.all().subscribe(res => {
      this.data = res;
      this.loading = false;
    }, err => Materialize.toast('Error al cargar Lotes', 4000));
  }

}
