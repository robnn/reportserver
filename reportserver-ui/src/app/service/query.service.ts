import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { PagedQueryRequest, PagedQueryRequests, NotPagedQueryRequest, Column, QueryRequests } from '../model/pagedQueryRequest';
import { Observable } from 'rxjs';
import { PagedQueryResponse, QueryResponse } from '../model/pagedQueryResponse';

@Injectable({
  providedIn: 'root'
})
export class QueryService {
  private connectionUrl = '/queries';

  constructor(private http: HttpClient,
    private userService: UserService) { }

  runQuery(connectionUuid: string, query: string) {
    return this.http.post(this.connectionUrl + '/simple' + '?connectionUuid=' + connectionUuid + '&query=' + query, null,
    { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  runPagedQuery(pagedQueryRequest: PagedQueryRequest): Observable<PagedQueryResponse> {
    const path = pagedQueryRequest.executionUuid ? '/execution' : '/paged';
    return this.http.post<PagedQueryResponse>(this.connectionUrl + path,
    pagedQueryRequest, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  runNonPagedQuery(pagedQueryRequest: NotPagedQueryRequest): Observable<QueryResponse> {
    return this.http.post<QueryResponse>(this.connectionUrl + '/parametrized',
    pagedQueryRequest, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  listSavedQueries(page: number, itemsPerPage: number) : Observable<PagedQueryRequests> {
    return this.http.get<PagedQueryRequests>(`${this.connectionUrl}?page=${page}&itemsPerPage=${itemsPerPage}`, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  listAllSavedQueries() : Observable<QueryRequests> {
    return this.http.get<QueryRequests>(`${this.connectionUrl}/all`, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  getColumns(pagedQueryRequest: PagedQueryRequest): Observable<Column[]> {
    return this.http.post<Column[]>(this.connectionUrl + '/getColumns',
    pagedQueryRequest, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }

  saveQuery(pagedQueryRequest: PagedQueryRequest): Observable<PagedQueryResponse> {
    return this.http.post<PagedQueryResponse>(this.connectionUrl + '/save',
    pagedQueryRequest, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
  }
}
