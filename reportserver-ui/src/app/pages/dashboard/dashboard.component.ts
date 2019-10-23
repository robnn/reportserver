import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';
import { DashboardService } from 'src/app/service/dashboard.service';
import { NotificationService } from 'src/app/service/notification.service';
import { MatDialog } from '@angular/material';
import { CreateDashboardComponent } from 'src/app/components/create-dashboard/create-dashboard.component';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  private dashboard: Dashboard;
  private dashboardExists: boolean;

  constructor(private userService: UserService,
    private router: Router,
    private dashboardService: DashboardService,
    private notificationService: NotificationService,
    public dialog: MatDialog) { }

  ngOnInit() {
    if (!this.userService.getAuthToken()) {
      this.router.navigate(['/login']);
    }
    this.getDashboard();
  }

  getDashboard() {
    this.userService.getCurrentUser().subscribe(user => {
      this.dashboardService.getDashboardForUsername(user.username).subscribe(resp => {
        this.dashboard = resp;
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
    const dialogRef = this.dialog.open(CreateDashboardComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result == 'SAVED') {
        this.getDashboard();
      }
    });
  }

}
