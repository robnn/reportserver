import { Component, OnInit } from '@angular/core';
import { DriverService } from 'src/app/driver.service';
import { Drivers } from 'src/app/model/driver';
import { MatDialog } from '@angular/material';
import { AddDialogComponent } from './add-dialog/add-dialog.component';

@Component({
  selector: 'app-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.css']
})
export class DriversComponent implements OnInit {

  public drivers: Drivers;

  constructor(private driverService: DriverService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.driverService.listDrivers().subscribe(resp => {
      this.drivers = resp;
    });
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddDialogComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === "SAVED"){
        this.ngOnInit();
      }
    });
  }

}
