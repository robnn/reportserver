import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { PagedQueryRequest } from './model/pagedQueryRequest';

@Injectable({
  providedIn: 'root'
})
export class QueryService {
  private connectionUrl = '/queries/';

  constructor(private http: HttpClient,
    private userService: UserService) { }

  runQuery(connectionUuid: string, query: string) {
    return this.http.post(this.connectionUrl + "simple" + "?connectionUuid=" + connectionUuid + "&query=" + query, null, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  runPagedQuery(pagedQueryRequest: PagedQueryRequest) {
    return this.http.post(this.connectionUrl + "paged", pagedQueryRequest, { headers: this.userService.getAuthTokenAsHttpHeader(null) })
  }
}
