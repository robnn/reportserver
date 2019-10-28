import { Component, OnInit, Input, ViewChild, OnChanges, SimpleChanges } from '@angular/core';
import { MatPaginator, PageEvent } from '@angular/material';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { QueryService } from 'src/app/service/query.service';
import { NotificationService } from 'src/app/service/notification.service';
import { PagedQueryResponse, QueryResponse } from 'src/app/model/pagedQueryResponse';

@Component({
  selector: 'app-result-table',
  templateUrl: './result-table.component.html',
  styleUrls: ['./result-table.component.css']
})
export class ResultTableComponent implements OnInit {

  @Input() public queryRequest: PagedQueryRequest;
  @Input() public onlyChart: boolean;
  @Input() public shouldExecuteOnCreation: boolean;
  @Input() public isOnDashboard: boolean;

  defaultPageSize = 10;
  defaultPageSizeOptions = [1, 2, 5, 10, 25, 100];
  defaultDashboardPageSize = 5;
  defaultDashboardPageSizeOptions = [5];
  params: any;
  neededPage = 1;
  itemsPerPage = 10;
  chartData: object[];
  chartLabels: object[];
  chartType: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  public queryResult: Array<Map<string, object>>;
  public objectValues = Object.values;
  public keys: string[];
  public isLoading = false;

  public totalRows: number;
  public queryResponse: PagedQueryResponse;
  public notPagedResponse: QueryResponse;


  constructor(private queryService: QueryService,
    private notificationService: NotificationService) { }

  ngOnInit() {
    this.queryRequest.itemsPerPage = this.pageSize();
    if (this.shouldExecuteOnCreation) {
      this.executeQuery(false);
    }
  }

  onPaginationChanged(pageEvent: PageEvent) {
    this.queryRequest.itemsPerPage = pageEvent.pageSize;
    this.queryRequest.neededPage = pageEvent.pageIndex + 1;
    this.executeQuery(false);
  }

  public executeQuery(resetPage: boolean) {    
    if (resetPage) {
      this.neededPage = 1;
      this.itemsPerPage = this.pageSize();
      this.isLoading = true;
    }
    
    this.queryResult = null;
    if (this.params){
      this.queryRequest.parameters = this.params;
    }
    this.queryService.runPagedQuery(this.queryRequest).subscribe(resp => {
      this.queryResponse = resp;
      this.queryResult = resp.pagedResult;
      this.totalRows = resp.totalItems;
      if (resp.totalItems !== 0) {
        this.keys = Object.keys(this.queryResult[0]);
      }
      if (this.queryResponse.charts.length > 0) {
        this.queryService.runNonPagedQuery(this.queryRequest).subscribe(notPagedResp => {
          this.notPagedResponse = notPagedResp;
          this.extractChartData();
        }, err => {
          this.notificationService.addNotification(err.error);
        });
      }
      this.isLoading = false;
    }, err => {
      this.notificationService.addNotification(err.error);
      this.isLoading = false;
    });

  }

  private extractChartData() {
    this.chartType = this.queryResponse.charts[0].chartType;
    this.chartData = this.notPagedResponse.result.map(it => it[this.queryResponse.charts[0].dataColumn]);
    this.chartLabels = this.notPagedResponse.result.map(it => it[this.queryResponse.charts[0].labelColumn]);
  }

  public executeQueryWithParams(params: object) {
    this.params = params;
    this.executeQuery(true);
  }

  pageSize() {
    return this.isOnDashboard ? this.defaultDashboardPageSize : this.defaultPageSize;
  }

  pageSizeOptions() {
    return this.isOnDashboard ? this.defaultDashboardPageSizeOptions : this.defaultPageSizeOptions;
  }
}
