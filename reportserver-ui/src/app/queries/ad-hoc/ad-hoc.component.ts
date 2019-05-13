import { Component, OnInit, ViewChild } from '@angular/core';
import { ConnectionService } from 'src/app/connection.service';
import { Connections } from 'src/app/model/connection';
import { NotificationService } from 'src/app/notification.service';
import { QueryService } from 'src/app/query.service';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';
import { PagedQueryResponse } from 'src/app/model/pagedQueryResponse';
import { ResultTableComponent } from '../result-table/result-table.component';
import { ParamHelper } from '../helper/param-helper';
import { MatDialog } from '@angular/material';
import { ParamModalComponent } from '../param-modal/param-modal.component';

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
  public shouldSave = false;
  public queryExecuted: boolean;

  constructor(private connectionService: ConnectionService,
    private notificationService: NotificationService,
    private queryService: QueryService,
    public dialog: MatDialog) { }

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

  openDialog(): void {
    const dialogRef = this.dialog.open(ParamModalComponent, {
      width: '400px',
      data: ParamHelper.extractParams(this.query)
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.queryExecuted = true;
        if (this.resultTable) {
          this.resultTable.executeQueryWithParams(null, result);
        }
      }
    });
  }

  executePagedQuery() {
    if (ParamHelper.isParametrized(this.query)) {
      this.openDialog();
    } else {
      this.queryExecuted = true;
      if (this.resultTable) {
        this.resultTable.executeQuery(null);
      }
    }
  }
}
