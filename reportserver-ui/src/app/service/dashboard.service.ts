import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { Dashboard } from '../model/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {

  private dashboardUrl = '/dashboards';

  constructor(private http: HttpClient,
    private userService: UserService) { }
  
    getDashboardForUsername(username: string) {
      return this.http.get<Dashboard>(`${this.dashboardUrl}?username=${username}`, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
    }

    manageDashboard(dashboard: Dashboard) {
      return this.http.post<Dashboard>(this.dashboardUrl, dashboard, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
    }
}
