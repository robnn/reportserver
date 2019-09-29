import { Component, OnInit, Input, ViewChild, AfterViewInit } from '@angular/core';
import { PagedQueryRequest, QueryVisibility, TeamUuidAndName } from 'src/app/model/pagedQueryRequest';
import { QueryService } from 'src/app/query.service';
import { NotificationService } from 'src/app/notification.service';
import { MatPaginator, PageEvent } from '@angular/material';
import { Chart } from 'src/app/model/pagedQueryRequest';

@Component({
  selector: 'app-result-table',
  templateUrl: './result-table.component.html',
  styleUrls: ['./result-table.component.css']
})
export class ResultTableComponent implements OnInit {

  @Input() connectionUuid: string;
  @Input() queryString: string;
  @Input() queryName: string;
  @Input() visibility: QueryVisibility;
  @Input() teamUuids: string[];

  queryRequest: PagedQueryRequest;
  params: any;
  neededPage = 1;
  itemsPerPage = 10;
  queryExecuted = false;
  chart: Chart;
  chartData: object[];
  chartLabels: object[];

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
    this.executeQuery(this.queryRequest, false, this.chart);
  }

  public executeQuery(query: PagedQueryRequest, resetPage: boolean, chart: Chart) {
    this.chart = chart;
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
    pagedQueryRequest.visibility = this.visibility;
    if (chart && chart.chartType != null) {
      pagedQueryRequest.charts = new Array(chart);
    }
    pagedQueryRequest.teamUuidsAndNames = this.teamUuids ? this.teamUuids.map(it => new TeamUuidAndName(it, null)) : query.teamUuidsAndNames;
    this.queryService.runPagedQuery(pagedQueryRequest).subscribe(resp => {
      this.queryResult = resp.pagedResult;
      if (resp.charts.length > 0) {
        this.chart = resp.charts[0];
        this.extractChartData();
      } else {
        this.chart = null;
      }
      if (resp.totalItems !== 0) {
        this.keys = Object.keys(this.queryResult[0]);
      }
      this.totalRows = resp.totalItems;
      this.queryExecuted = false;
    }, err => {
      this.notificationService.addNotification(err.error);
    });
  }

  private extractChartData() {
    this.chartData = this.queryResult.map(it => it[this.chart.dataColumn]);
    this.chartLabels = this.queryResult.map(it => it[this.chart.labelColumn]);
  }

  public executeQueryWithParams(query: PagedQueryRequest, params: object) {
    this.params = params;
    this.executeQuery(query, true, this.chart);
  }
}
