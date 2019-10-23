import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';
import { DashboardService } from 'src/app/service/dashboard.service';
import { NotificationService } from 'src/app/service/notification.service';
import { MatDialog } from '@angular/material';
import { CreateDashboardComponent } from 'src/app/components/edit-dashboard/create-dashboard.component';
import { Dashboard } from 'src/app/model/dashboard';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { QueryService } from 'src/app/service/query.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private dashboard: Dashboard;
  private dashboardExists: boolean;
  private username: string;
  private queries: PagedQueryRequest[];

  constructor(private userService: UserService,
    private router: Router,
    private dashboardService: DashboardService,
    private notificationService: NotificationService,
    public dialog: MatDialog,
    private queryService: QueryService) { }

  ngOnInit() {
    if (!this.userService.getAuthToken()) {
      this.router.navigate(['/login']);
    }
    this.getDashboard();
    this.queryService.listAllSavedQueries().subscribe(resp => {
      this.queries = resp.queries;
    })
  }

  getDashboard() {
    this.userService.getCurrentUser().subscribe(user => {
      this.username = user.username;
      this.dashboardService.getDashboardForUsername(user.username).subscribe(resp => {
        this.dashboard = resp;
        this.dashboard.dashboardQueries = resp.dashboardQueries.sort((a,b) => a.order - b.order);
        this.dashboardExists = true;
      }, error => {
        if (error.error.message === 'DASHBOARD_FOR_USER_NOT_FOUND') {
          this.dashboardExists = false;
        } else {
          this.notificationService.addNotification(error.error);
        }
      })
    });
  }

  openCreateDashboardModal() {
    let dashboard = this.dashboard;
    if (!dashboard) {
      dashboard = new Dashboard();
      dashboard.userName = this.username;
    }
    const dialogRef = this.dialog.open(CreateDashboardComponent, {
      width: '400px',
      maxHeight: '90vh',
      data: dashboard 
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'SAVED') {
        this.getDashboard();
      }
    });
  }

  getQueryForUuid(uuid: string) {
    if (this.queries) {
      return this.queries.filter(query => query.uuid == uuid)[0];
    }
  }

}
