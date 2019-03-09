import { Component, OnInit } from '@angular/core';
import { DriverService } from 'src/app/driver.service';
import { Drivers } from 'src/app/model/driver';

@Component({
  selector: 'app-drivers',
  templateUrl: './drivers.component.html',
  styleUrls: ['./drivers.component.css']
})
export class DriversComponent implements OnInit {

  public drivers: Drivers;

  constructor(private driverService: DriverService) { }

  ngOnInit() {
    this.driverService.listDrivers().subscribe(resp => {
      this.drivers = resp;
    });
  }

}
