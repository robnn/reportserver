import { Component, OnInit, ViewChild } from '@angular/core';
import { ConnectionService } from 'src/app/connection.service';
import { Connections } from 'src/app/model/connection';
import { NotificationService } from 'src/app/notification.service';
import { QueryService } from 'src/app/query.service';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { PagedQueryResponse } from 'src/app/model/pagedQueryResponse';
import { ResultTableComponent } from '../result-table/result-table.component';

@Component({
  selector: 'app-ad-hoc',
  templateUrl: './ad-hoc.component.html',
  styleUrls: ['./ad-hoc.component.css']
})
export class AdHocComponent implements OnInit {

  @ViewChild(ResultTableComponent) resultTable: ResultTableComponent;

  public connections: Connections;
  public connectionUuid: string;
  public query: string;
  public queryExecuted: boolean;

  constructor(private connectionService: ConnectionService,
    private notificationService: NotificationService,
    private queryService: QueryService) { }

  ngOnInit() {
    this.connectionService.listConnections().subscribe(resp => {
      this.connections = resp;
    }, err => this.notificationService.addNotification(err.error));
  }

  executeQuery() {
    this.queryService.runQuery(this.connectionUuid, this.query).subscribe(resp => {
      console.log(resp);
    });
  }

  executePagedQuery() {
    this.queryExecuted = true;
    if (this.resultTable) {
      this.resultTable.executeQuery();
    }
  }
}
