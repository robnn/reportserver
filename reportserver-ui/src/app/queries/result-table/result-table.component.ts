import { Component, OnInit, Input, ViewChild, AfterViewInit } from '@angular/core';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { QueryService } from 'src/app/query.service';
import { NotificationService } from 'src/app/notification.service';
import { MatPaginator, PageEvent } from '@angular/material';

@Component({
  selector: 'app-result-table',
  templateUrl: './result-table.component.html',
  styleUrls: ['./result-table.component.css']
})
export class ResultTableComponent implements OnInit {

  @Input() connectionUuid: string;
  @Input() queryString: string;
  @Input() queryName: string;
  queryRequest: PagedQueryRequest;
  params: any;
  neededPage = 1;
  itemsPerPage = 10;
  queryExecuted = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  public queryResult: Array<Map<string, object>>;
  public objectValues = Object.values;
  public keys: string[];

  public totalRows: number;

  constructor(private queryService: QueryService,
    private notificationService: NotificationService) { }

  ngOnInit() {
  }

  onPaginationChanged(pageEvent: PageEvent) {
    this.itemsPerPage = pageEvent.pageSize;
    this.neededPage = pageEvent.pageIndex + 1;
    this.executeQuery(this.queryRequest, false);
  }

  public executeQuery(query: PagedQueryRequest, resetPage: boolean) {
    if (resetPage) {
      this.neededPage = 1;
      this.itemsPerPage = 10;
    }
    this.queryResult = null;
    this.queryExecuted = true;
    this.queryRequest = query;
    const pagedQueryRequest = this.queryRequest || new PagedQueryRequest();
    if (!this.queryRequest) {
      pagedQueryRequest.connectionUuid = this.connectionUuid;
      pagedQueryRequest.queryString = this.queryString;
      if (this.queryName) {
        pagedQueryRequest.queryName = this.queryName;
      }
    }
    pagedQueryRequest.itemsPerPage = this.itemsPerPage;
    pagedQueryRequest.neededPage = this.neededPage;
    pagedQueryRequest.parameters = this.params;
    this.queryService.runPagedQuery(pagedQueryRequest).subscribe(resp => {
      this.queryResult = resp.pagedResult;
      if (resp.totalItems !== 0) {
        this.keys = Object.keys(this.queryResult[0]);
      }
      this.totalRows = resp.totalItems;
      this.queryExecuted = false;
    }, err => {
      this.notificationService.addNotification(err.error);
    });
  }

  public executeQueryWithParams(query: PagedQueryRequest, params: object) {
    this.params = params;
    this.executeQuery(query, true);
  }
}
