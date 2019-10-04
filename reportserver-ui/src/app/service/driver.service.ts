import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { Drivers, DbType } from '../model/driver';
import { Observable } from 'rxjs';

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

  createDriver(file: File, dbType: DbType): Observable<Object> {
    const formData: FormData = new FormData();
    formData.append('multipartFile', file);
    formData.append('dbType', dbType);
    return this.http.post(this.driversUrl, formData, {headers : this.userService.getAuthTokenAsHttpHeader(null)});
  }
}
