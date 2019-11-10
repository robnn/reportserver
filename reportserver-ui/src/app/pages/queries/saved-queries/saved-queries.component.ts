import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { QueryService } from 'src/app/service/query.service';
import { ConnectionService } from 'src/app/service/connection.service';
import { NotificationService } from 'src/app/service/notification.service';
import { Connections } from 'src/app/model/connection';
import { TranslateService } from '@ngx-translate/core';
import { ParamHelper } from '../helper/param-helper';
import { MatDialog, MatTabGroup, PageEvent } from '@angular/material';
import { ParamModalComponent } from '../param-modal/param-modal.component';
import { PagedQueryRequest, QueryVisibility } from 'src/app/model/pagedQueryRequest';
import { ResultTableComponent } from '../../../components/result-table/result-table.component';
import { ExcelService } from 'src/app/service/excel.service';
import { RequestHelper } from '../helper/request-helper';
import { ModalQueryEditorComponent } from 'src/app/components/modal-query-editor/modal-query-editor.component';
import { ChartType } from 'src/app/model/chart-type';
import { ScheduledExecutionType } from 'src/app/model/scheduled-type';
import { Day } from 'src/app/model/day';

@Component({
  selector: 'app-saved-queries',
  templateUrl: './saved-queries.component.html',
  styleUrls: ['./saved-queries.component.css']
})
export class SavedQueriesComponent implements OnInit {

  @ViewChild(ResultTableComponent) resultTable: ResultTableComponent;

  public queries: Array<PagedQueryRequest>;
  public connections: Connections;
  public arrayFrom = Array.from;
  public currentQueryRequest = new PagedQueryRequest();
  public types = Object.keys(QueryVisibility);
  public chartTypes = Object.keys(ChartType);
  public scheduleTypes = Object.keys(ScheduledExecutionType);
  public dayTypes = Object.keys(Day);
  public totalQueries: number;
  public itemsPerPage: number = 10;
  public neededPage: number = 1;


  constructor(private queryService: QueryService,
    private connectionService: ConnectionService,
    private notificationService: NotificationService,
    private translateService: TranslateService,
    public dialog: MatDialog,
    private excelService: ExcelService) { }

  ngOnInit() {
    this.connectionService.listConnections().subscribe(resp => {
      this.connections = resp;
    }, err => this.notificationService.addNotification(err.error));
    this.getQueries();
  }

  getQueries() {
    this.queryService.listSavedQueries(this.neededPage, this.itemsPerPage).subscribe(resp => {
      this.queries = resp.queries.map((q) => {
        q.arrayParameters = ParamHelper.mapToArray(q.parameters);
        return q;
      });
      this.totalQueries = resp.totalItems;
    });
  }

  getConnectionInfoForUuid(connectionUuid: string): string {
    if (this.connections) {
      const connection = this.connections.connections.find(conn => {
        return conn.uuid === connectionUuid;
      });
      return `${this.translateService.instant(connection.driverType)} @ ${connection.host}:${connection.port} - ${connection.dbName}`;
    }
    return '';
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(ParamModalComponent, {
      width: '400px',
      data: ParamHelper.keyArrayToMap(this.currentQueryRequest.arrayParameters)
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        if (this.resultTable) {
          this.resultTable.executeQueryWithParams(result);
        }
      }
    });
  }

  openDialogForExport(query: PagedQueryRequest): void {
    const dialogRef = this.dialog.open(ParamModalComponent, {
      width: '400px',
      data: ParamHelper.keyArrayToMap(query.arrayParameters)
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        const notPaged = RequestHelper.convertToNotPagedRequest(query);
        this.queryService.runNonPagedQuery(notPaged).subscribe(resp => {
          this.excelService.exportAsExcelFile(resp.result, query.queryName);
        });
      }
    });
  }

  executePagedQuery(query: PagedQueryRequest) {
    this.currentQueryRequest.setAllFrom(query);
    
    if (ParamHelper.isParametrized(query.queryString)) {
      this.openDialog();
    } else {
      if (this.resultTable) {
        this.resultTable.executeQuery(true);
      }
    }
    if (query.saveExecution) {
      this.getQueries();
    }
  }

  exportQuery(query: PagedQueryRequest) {
    if (ParamHelper.isParametrized(query.queryString)) {
      this.openDialogForExport(query);
    } else {
      const notPaged = RequestHelper.convertToNotPagedRequest(query);
      this.queryService.runNonPagedQuery(notPaged).subscribe(resp => {
        this.excelService.exportAsExcelFile(resp.result, query.queryName);
      });
    }
  }

  openForEditing(query: PagedQueryRequest) {
    const dialogRef = this.dialog.open(ModalQueryEditorComponent, {
      width: '80%',
      maxHeight: '90vh',
      data: query,
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === "SAVED"){
        console.log("SAVED");
        
        this.ngOnInit();
      }
    });
  }

  onPaginationChanged(pageEvent: PageEvent) {
    this.itemsPerPage = pageEvent.pageSize;
    this.neededPage = pageEvent.pageIndex + 1;
    this.getQueries();
  }

  getExecutionForQuery(query: PagedQueryRequest, executionUuid: string) {
    this.currentQueryRequest.setAllFrom(query);
    this.currentQueryRequest.executionUuid = executionUuid;
    if (this.resultTable) {
      this.resultTable.executeQuery(true);
    }
    
  }
}
