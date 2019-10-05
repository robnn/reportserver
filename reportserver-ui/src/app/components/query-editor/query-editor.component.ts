import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Connections } from 'src/app/model/connection';
import { PagedQueryRequest, QueryVisibility, Column, ChartDiagram, Parameter } from 'src/app/model/pagedQueryRequest';
import { Team } from 'src/app/model/team';
import { ChartType } from 'src/app/model/chart-type';
import { ConnectionService } from 'src/app/service/connection.service';
import { NotificationService } from 'src/app/service/notification.service';
import { QueryService } from 'src/app/service/query.service';
import { MatDialog } from '@angular/material';
import { TeamService } from 'src/app/service/team.service';
import { ParamModalComponent } from 'src/app/pages/queries/param-modal/param-modal.component';
import { ParamHelper } from 'src/app/pages/queries/helper/param-helper';
import { ResultTableComponent } from '../result-table/result-table.component';

@Component({
  selector: 'app-query-editor',
  templateUrl: './query-editor.component.html',
  styleUrls: ['./query-editor.component.css']
})
export class QueryEditorComponent implements OnInit {

  @Output() public onSave: EventEmitter<any> = new EventEmitter()
  @Input() allowExecution = true;
  @Input() queryRequest: PagedQueryRequest;
  @Input() resultTable: ResultTableComponent;

  public connections: Connections;
  public shouldChart = false;
  public teams: Array<Team>;
  public visibilityTypes = Object.keys(QueryVisibility);
  public chartTypes = Object.keys(ChartType);
  public chartColumns: Column[];
  
  constructor(private connectionService: ConnectionService,
    private notificationService: NotificationService,
    private queryService: QueryService,
    public dialog: MatDialog,
    private teamService: TeamService) { }

  ngOnInit() {
    this.connectionService.listConnections().subscribe(resp => {
      this.connections = resp;
    }, err => this.notificationService.addNotification(err.error));
    this.teamService.listTeams().subscribe(resp => {
      this.teams = resp.teams;
    }, err => this.notificationService.addNotification(err.error));
    if (this.queryRequest.charts.length > 0) {
      this.shouldChart = true;
      this.queryService.getColumns(this.queryRequest).subscribe(resp => {
        this.chartColumns = resp;
      });
    }
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(ParamModalComponent, {
      width: '400px',
      data: ParamHelper.keyArrayToMap(ParamHelper.extractParams(this.queryRequest.queryString).map(key => Parameter.of(key)))
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.queryRequest.parameters = result;
        if (this.resultTable) {
          this.resultTable.executeQuery(true);
        }
      }
    });
  }

  onShouldChartChange() {
    if (this.shouldChart) {
      if (this.queryRequest.charts.length == 0) {
        this.queryRequest.charts.push(new ChartDiagram());
      }
      this.queryService.getColumns(this.queryRequest).subscribe(resp => {
        this.chartColumns = resp;
      });
    } else {
      this.queryRequest.charts.unshift();
    }
  }

  onQueryChange() {
    this.shouldChart = false;
  }

  executePagedQuery() {
    if (ParamHelper.isParametrized(this.queryRequest.queryString)) {
      this.openDialog();
    } else {
      if (this.resultTable) {
        this.resultTable.executeQuery(true);
      }
    }
  }

  saveQuery() {
    this.queryService.saveQuery(this.queryRequest).subscribe(resp => {
      this.onSave.emit(resp);
    }, error => this.notificationService.addNotification(error.error))
  }
}
