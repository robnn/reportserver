import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { Connections, Connection } from './model/connection';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ConnectionService {

  private connectionUrl = '/connections/';

  constructor(private http: HttpClient,
    private userService: UserService) { }

  listConnections() {
    return this.http.get<Connections>(this.connectionUrl, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  createConnection(connection: Connection) {
    return this.http.post(this.connectionUrl, connection, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  runQuery(connectionUuid: string, query: string) {
    return this.http.post(this.connectionUrl + "query" + "?connectionUuid=" + connectionUuid + "&query=" + query, null, { headers: this.userService.getAuthTokenAsHttpHeader(null) } );
  }

}
