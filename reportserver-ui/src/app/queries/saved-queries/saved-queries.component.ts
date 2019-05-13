import { Component, OnInit, ViewChild } from '@angular/core';
import { QueryService } from 'src/app/query.service';
import { ConnectionService } from 'src/app/connection.service';
import { NotificationService } from 'src/app/notification.service';
import { Connections } from 'src/app/model/connection';
import { TranslateService } from '@ngx-translate/core';
import { ParamHelper } from '../helper/param-helper';
import { MatDialog } from '@angular/material';
import { ParamModalComponent } from '../param-modal/param-modal.component';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { ResultTableComponent } from '../result-table/result-table.component';

@Component({
  selector: 'app-saved-queries',
  templateUrl: './saved-queries.component.html',
  styleUrls: ['./saved-queries.component.css']
})
export class SavedQueriesComponent implements OnInit {

  public queries;
  public connections: Connections;
  public arrayFrom = Array.from;
  public currentQueryRequest: PagedQueryRequest;
  public queryExecuted = false;

  @ViewChild(ResultTableComponent) resultTable: ResultTableComponent;


  constructor(private queryService: QueryService,
    private connectionService: ConnectionService,
    private notificationService: NotificationService,
    private translateService: TranslateService,
    public dialog: MatDialog) { }

  ngOnInit() {
    this.connectionService.listConnections().subscribe(resp => {
      this.connections = resp;
    }, err => this.notificationService.addNotification(err.error));
    this.queryService.listSavedQueries().subscribe(resp => {
      this.queries = resp.queries.map((q) => {
        q.arrayParameters = ParamHelper.mapToArray(q.parameters);
        return q;
      });
    });
  }

  getConnectionInfoForUuid(connectionUuid): string {
    if (this.connections) {
      const connection = this.connections.connections.find(conn => {
        return conn.uuid === connectionUuid;
      });
      return `${this.translateService.instant(connection.driverType)} @ ${connection.host}:${connection.port} - ${connection.dbName}`;
    }
    return '';
  }

  openDialog(query: string): void {
    const dialogRef = this.dialog.open(ParamModalComponent, {
      width: '400px',
      data: ParamHelper.extractParams(query)
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.queryExecuted = true;
        if (this.resultTable) {
          this.resultTable.executeQueryWithParams(this.currentQueryRequest, result);
        }
      }
    });
  }

  executePagedQuery(query: PagedQueryRequest) {
    this.currentQueryRequest = query;
    if (ParamHelper.isParametrized(query.queryString)) {
      this.openDialog(query.queryString);
    } else {
      this.queryExecuted = true;
      if (this.resultTable) {
        this.resultTable.executeQuery(query);
      }
    }
  }

}
