import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from 'src/app/pages/login/login.component';
import { RegisterComponent } from 'src/app/pages/register/register.component';
import { DashboardComponent } from 'src/app/pages/dashboard/dashboard.component';
import { SettingsComponent } from 'src/app/pages/settings/settings.component';
import { QueriesComponent } from 'src/app/pages/queries/queries.component';
import { LoggedInGuard } from './guards/logged-in.guard';
import { NotLoggedInGuard } from './guards/not-logged-in.guard';

const appRoutes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [NotLoggedInGuard], data: { animation: 'LoginPage' } },
  { path: 'register', component: RegisterComponent, canActivate: [NotLoggedInGuard], data: { animation: 'RegisterPage' } },
  { path: 'dashboard', component: DashboardComponent, canActivate: [LoggedInGuard], data: { animation: 'DashboardPage' } },
  { path: 'settings', component: SettingsComponent, canActivate: [LoggedInGuard], data: { animation: 'SettingsPage' } },
  { path: 'queries', component: QueriesComponent, canActivate: [LoggedInGuard], data: {animation: 'QueriesPage' } },
  { path: '', redirectTo: 'login', pathMatch: 'full' }
];

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class RoutingModule { }
