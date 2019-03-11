import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import {
  MatInputModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatCardModule,
  MatButtonModule, MatToolbarModule, MatMenuModule, MatIconModule, MatSnackBarModule,
  MatTabsModule, MatExpansionModule, MatDialogModule
} from '@angular/material';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS } from '@angular/common/http';
import { TranslateModule, TranslateLoader } from '@ngx-translate/core';
import { TranslateHttpLoader } from '@ngx-translate/http-loader';
import { NgxFlagIconCssModule } from 'ngx-flag-icon-css'
import { StorageServiceModule } from 'angular-webstorage-service';
import { FlexLayoutModule } from '@angular/flex-layout';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ApiInterceptor } from './api-interceptor';
import { SeveritySnackbarComponent } from './severity-snackbar/severity-snackbar.component';
import { SettingsComponent } from './settings/settings.component';
import { DriversComponent } from './settings/drivers/drivers.component';
import { AddDialogComponent } from './settings/drivers/add-dialog/add-dialog.component';


const appRoutes: Routes = [
  { path: 'login', component: LoginComponent, data: { animation: 'LoginPage' } },
  { path: 'register', component: RegisterComponent, data: { animation: 'RegisterPage' } },
  { path: 'dashboard', component: DashboardComponent, data: { animation: 'DashboardPage' } },
  { path: 'settings', component: SettingsComponent, data: { animation: 'SettingsPage' } },
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
    AddDialogComponent
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
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ApiInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [SeveritySnackbarComponent, AddDialogComponent]
})
export class AppModule { }
