import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import {
  MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatCardModule,
  MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatSnackBarModule,
  MatTabsModule, MatExpansionModule, MatDialogModule, MatPaginatorModule, MatTableModule,
  MatCheckboxModule,
  MatProgressSpinnerModule
} from '@angular/material';
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

export function hljsLanguages() {
  return [
    {name: 'sql', func: sql}
  ];
}

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent, data: { animation: 'LoginPage' } },
  { path: 'register', component: RegisterComponent, data: { animation: 'RegisterPage' } },
  { path: 'dashboard', component: DashboardComponent, data: { animation: 'DashboardPage' } },
  { path: 'settings', component: SettingsComponent, data: { animation: 'SettingsPage' } },
  { path: 'queries', component: QueriesComponent, data: {animation: 'QueriesPage' } },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];

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
    BarChartComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes, { enableTracing: false }),
    BrowserModule,
    NgxFlagIconCssModule,
    MatFormFieldModule,
    MatOptionModule,
    MatSelectModule,
    MatInputModule,
    MatButtonModule,
    MatToolbarModule,
    MatMenuModule,
    MatIconModule,
    MatSnackBarModule,
    MatCardModule,
    MatTabsModule,
    MatExpansionModule,
    MatDialogModule,
    MatPaginatorModule,
    MatTableModule,
    MatCheckboxModule,
    MatProgressSpinnerModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    StorageServiceModule,
    FlexLayoutModule,
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
  entryComponents: [SeveritySnackbarComponent, AddDialogComponent, AddConnectionDialogComponent, ParamModalComponent, EditTeamComponent]
})
export class AppModule { }
