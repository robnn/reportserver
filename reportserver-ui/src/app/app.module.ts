import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { NgxFlagIconCssModule } from 'ngx-flag-icon-css'
import { StorageServiceModule } from 'angular-webstorage-service';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HighlightModule } from 'ngx-highlightjs';

import sql from 'highlight.js/lib/languages/sql';

import { AppComponent } from './app.component';
import { RegisterComponent } from './pages/register/register.component';
import { ApiInterceptor } from './api-interceptor';
import { SeveritySnackbarComponent } from './components/severity-snackbar/severity-snackbar.component';
import { SettingsComponent } from './pages/settings/settings.component';
import { DriversComponent } from './pages/settings/drivers/drivers.component';
import { AddDialogComponent } from './pages/settings/drivers/add-dialog/add-dialog.component';
import { ConnectionsComponent } from './pages/settings/connections/connections.component';
import { AddConnectionDialogComponent } from './pages/settings/connections/add-connection-dialog/add-connection-dialog.component';
import { QueriesComponent } from './pages/queries/queries.component';
import { AdHocComponent } from './pages/queries/ad-hoc/ad-hoc.component';
import { ResultTableComponent } from './components/result-table/result-table.component';
import { ParamModalComponent } from './pages/queries/param-modal/param-modal.component';
import { SavedQueriesComponent } from './pages/queries/saved-queries/saved-queries.component';
import { TeamsComponent } from './pages/settings/teams/teams.component';
import { EditTeamComponent } from './pages/settings/teams/edit-team/edit-team.component';
import { ChartsModule } from 'ng2-charts';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PieChartComponent } from './components/charts/pie-chart/pie-chart.component';
import { QueryEditorComponent } from './components/query-editor/query-editor.component';
import { BarChartComponent } from './components/charts/bar-chart/bar-chart.component';
import { ModalQueryEditorComponent } from './components/modal-query-editor/modal-query-editor.component';
import { CreateDashboardComponent } from './components/edit-dashboard/create-dashboard.component';
import { RoutingModule } from './modules/routing/routing.module';
import { MaterialModule } from './modules/material/material.module';
import { MaterialTimePickerModule } from '@candidosales/material-time-picker';

export function hljsLanguages() {
  return [
    {name: 'sql', func: sql}
  ];
}

export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http);
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    DashboardComponent,
    SeveritySnackbarComponent,
    SettingsComponent,
    DriversComponent,
    AddDialogComponent,
    ConnectionsComponent,
    AddConnectionDialogComponent,
    QueriesComponent,
    AdHocComponent,
    ResultTableComponent,
    ParamModalComponent,
    SavedQueriesComponent,
    TeamsComponent,
    EditTeamComponent,
    PieChartComponent,
    QueryEditorComponent,
    BarChartComponent,
    ModalQueryEditorComponent,
    CreateDashboardComponent
  ],
  imports: [
    MaterialModule,
    RoutingModule,
    BrowserModule,
    NgxFlagIconCssModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    StorageServiceModule,
    FlexLayoutModule,
    MaterialTimePickerModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    }),
    HighlightModule.forRoot({
      languages: hljsLanguages
    }),
    ChartsModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [SeveritySnackbarComponent, AddDialogComponent, AddConnectionDialogComponent, 
    ParamModalComponent, EditTeamComponent, ModalQueryEditorComponent, CreateDashboardComponent]
})
export class AppModule { }
