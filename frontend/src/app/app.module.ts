import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { ReportComponent } from './reports/report.component';
import { ReportService } from './reports/shared/_index';

@NgModule({
  declarations: [
    AppComponent, ReportComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [ReportService],
  bootstrap: [AppComponent]
})
export class AppModule { }
