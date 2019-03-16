import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { ConnectionService } from 'src/app/connection.service';
import { NotificationService } from 'src/app/notification.service';
import { DriverService } from 'src/app/driver.service';
import { Drivers } from 'src/app/model/driver';
import { Connection } from 'src/app/model/connection';

@Component({
  selector: 'app-add-connection-dialog',
  templateUrl: './add-connection-dialog.component.html',
  styleUrls: ['./add-connection-dialog.component.css']
})
export class AddConnectionDialogComponent implements OnInit {

  public drivers: Drivers;
  public connection: Connection = new Connection();

  constructor(
    public dialogRef: MatDialogRef<AddConnectionDialogComponent>,
    private connectionService: ConnectionService,
    private driverService: DriverService,
    private notificationService: NotificationService) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  createClick(): void {
    this.connectionService.createConnection(this.connection).subscribe(data => {
      this.dialogRef.close("SAVED");
    }, error => {
      this.notificationService.addNotification(error.error);
    });
  }


  ngOnInit() {
    this.driverService.listDrivers().subscribe(resp => {
      this.drivers = resp;
    }, err => this.notificationService.addNotification(err.error));
  }

}
