import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { Drivers } from './model/driver';

@Injectable({
  providedIn: 'root'
})
export class DriverService {

  private driversUrl = '/drivers/';

  constructor(private http: HttpClient,
    private userService: UserService) { }

  listDrivers() {
    return this.http.get<Drivers>(this.driversUrl, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }
}
