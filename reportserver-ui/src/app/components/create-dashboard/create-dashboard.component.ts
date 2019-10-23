import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { Dashboard, DashboardQuery } from '../../model/dashboard';

@Component({
  selector: 'app-create-dashboard',
  templateUrl: './create-dashboard.component.html',
  styleUrls: ['./create-dashboard.component.css']
})
export class CreateDashboardComponent implements OnInit {

  public dashboard: Dashboard = new Dashboard();

  constructor(public dialogRef: MatDialogRef<CreateDashboardComponent>) { }

  ngOnInit() {
    this.dashboard.dashboardQueries = [];
  }

  onSaveAction(event: Event) {   
    this.dialogRef.close('SAVED');
  }

  addDashboardQuery() {
    this.dashboard.dashboardQueries.push(new DashboardQuery());
  }
}
