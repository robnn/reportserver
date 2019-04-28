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
  @Input() neededPage: number;
  @Input() itemsPerPage: number;
  @Input() queryName: string;
  params: any;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  public queryResult: Array<Map<string, object>>;
  public objectValues = Object.values;
  public keys;

  public totalRows: number;

  constructor(private queryService: QueryService,
    private notificationService: NotificationService) { }

  ngOnInit() {
    const pagedQueryRequest = new PagedQueryRequest();
    console.log(this.params);
    pagedQueryRequest.connectionUuid = this.connectionUuid;
    pagedQueryRequest.queryString = this.queryString;
    pagedQueryRequest.itemsPerPage = this.itemsPerPage;
    pagedQueryRequest.neededPage = this.neededPage;
    pagedQueryRequest.parameters = this.params;
    if (this.queryName) {
      pagedQueryRequest.queryName = this.queryName;
    }
    this.queryService.runPagedQuery(pagedQueryRequest).subscribe(resp => {
      this.queryResult = resp.pagedResult;
      if (resp.totalItems !== 0) {
        this.keys = Object.keys(this.queryResult[0]);
      }
      this.totalRows = resp.totalItems;
    }, err => {
      this.notificationService.addNotification(err.error);
    })
  }

  onPaginationChanged(pageEvent: PageEvent) {
    this.itemsPerPage = pageEvent.pageSize;
    this.neededPage = pageEvent.pageIndex + 1;
    this.executeQuery();
  }

  public executeQuery() {
    this.ngOnInit();
  }

  public executeQueryWithParams(params: object) {
    this.params = params;
    this.ngOnInit();
  }
}
