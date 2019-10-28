import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';
import { DashboardService } from 'src/app/service/dashboard.service';
import { NotificationService } from 'src/app/service/notification.service';
import { MatDialog } from '@angular/material';
import { CreateDashboardComponent } from 'src/app/components/edit-dashboard/create-dashboard.component';
import { Dashboard, DashboardQuery } from 'src/app/model/dashboard';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { QueryService } from 'src/app/service/query.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private dashboard: Dashboard;
  private username: string;
  private queries: PagedQueryRequest[];
  private gridBreakpoint: number;

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
    this.gridBreakpoint = this.calcGridCols(window.innerWidth);
  }

  onResize(event: { target: { innerWidth: number; }; }) {
    this.gridBreakpoint = this.calcGridCols(event.target.innerWidth);
  }

  calcGridCols(width: number) {
    if (width >= 1430) {
      return 3;
    } else if (width <= 1430 && width >= 1000) {
      return 2;
    } else {
      return 1;
    }
  }

  getDashboard() {
    this.userService.getCurrentUser().subscribe(user => {
      this.username = user.username;
      this.dashboardService.getDashboardForUsername(user.username).subscribe(resp => {
        this.dashboard = resp;
        this.dashboard.dashboardQueries = resp.dashboardQueries.sort((a, b) => a.order - b.order);
      }, error => {
        if (error.error.message !== 'DASHBOARD_FOR_USER_NOT_FOUND') {
          this.notificationService.addNotification(error.error);
        }
      })
    });
  }

  openEditDashboardModal(query: DashboardQuery) {
    let dashboard = this.dashboard;
    if (!dashboard) {
      dashboard = new Dashboard();
      dashboard.userName = this.username;
    }
    let dashboardQuery = query;
    if (!query) {
      dashboardQuery = new DashboardQuery();
      dashboardQuery.order = this.dashboard.dashboardQueries.length;
      dashboardQuery.chart = false;
    }
    const dialogRef = this.dialog.open(CreateDashboardComponent, {
      width: '400px',
      maxHeight: '90vh',
      data: dashboardQuery
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'SAVED') {
        this.dashboard.dashboardQueries.push(dashboardQuery);
        this.dashboardService.manageDashboard(this.dashboard).subscribe(() => {
          this.getDashboard();
        }, error => this.notificationService.addNotification(error.error));
      }
    });
  }

  getQueryForUuid(uuid: string) {
    if (this.queries) {
      return this.queries.filter(query => query.uuid == uuid)[0];
    }
  }

  removeDash(query: DashboardQuery) {
    const index = this.dashboard.dashboardQueries.indexOf(query);
    if (index !== -1) {
      this.dashboard.dashboardQueries.splice(index, 1);
      this.saveDashboard();
    }
    
  }

  saveDashboard() {
    this.dashboardService.manageDashboard(this.dashboard).subscribe(() => {
      this.getDashboard();
    }, error => this.notificationService.addNotification(error.error));
  }
}
