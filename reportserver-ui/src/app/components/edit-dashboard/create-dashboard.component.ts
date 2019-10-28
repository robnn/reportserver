import { Component, OnInit, Inject, Input } from '@angular/core';
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

  public dashboardQuery: DashboardQuery;
  public queries: PagedQueryRequest[];

  constructor(public dialogRef: MatDialogRef<CreateDashboardComponent>,
    private queryService: QueryService,
    private dashboardService: DashboardService,
    private notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) public inDashboardQuery: DashboardQuery) { 
      this.dashboardQuery = inDashboardQuery;
     }

  ngOnInit() {
    this.queryService.listAllSavedQueries().subscribe(resp => {
      this.queries = resp.queries;
    })
  }

  isChartByQueryUuid(uuid: string) {
    if (uuid == null || !this.queries) return false;
    return this.queries.filter(query => query.uuid == uuid)[0].charts.length != 0;
  }

  saveDashboard() {
    this.dialogRef.close('SAVED');
  }
}
