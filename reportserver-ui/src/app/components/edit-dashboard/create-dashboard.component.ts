import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { Dashboard, DashboardQuery } from '../../model/dashboard';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { QueryService } from 'src/app/service/query.service';
import { DashboardService } from 'src/app/service/dashboard.service';
import { NotificationService } from 'src/app/service/notification.service';

@Component({
  selector: 'app-create-dashboard',
  templateUrl: './create-dashboard.component.html',
  styleUrls: ['./create-dashboard.component.css']
})
export class CreateDashboardComponent implements OnInit {

  public dashboard: Dashboard = new Dashboard();
  public queries: PagedQueryRequest[];

  constructor(public dialogRef: MatDialogRef<CreateDashboardComponent>,
    private queryService: QueryService,
    private dashboardService: DashboardService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) public inDashboard: Dashboard) { 
      this.dashboard = inDashboard;
     }

  ngOnInit() {
    if (!this.dashboard.dashboardQueries) {
      this.dashboard.dashboardQueries = [];
    }
    this.queryService.listAllSavedQueries().subscribe(resp => {
      this.queries = resp.queries;
    })
  }

  addDashboardQuery() {
    const dashboardQuery = new DashboardQuery();
    dashboardQuery.order = this.dashboard.dashboardQueries.length;
    dashboardQuery.chart = false;
    this.dashboard.dashboardQueries.push(dashboardQuery);
  }

  isChartByQueryUuid(uuid: string) {
    if (uuid == null || !this.queries) return false;
    return this.queries.filter(query => query.uuid == uuid)[0].charts.length != 0;
  }

  saveDashboard() {
    this.dashboardService.manageDashboard(this.dashboard).subscribe(() => {
      this.dialogRef.close('SAVED');
    }, error => this.notificationService.addNotification(error.error));
  }
}
