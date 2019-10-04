import { Component, OnInit, ViewChild } from '@angular/core';
import { ConnectionService } from 'src/app/service/connection.service';
import { Connections } from 'src/app/model/connection';
import { NotificationService } from 'src/app/service/notification.service';
import { QueryService } from 'src/app/service/query.service';
import { PagedQueryRequest, Parameter, QueryVisibility, Column, ChartDiagram } from 'src/app/model/pagedQueryRequest';
import { ResultTableComponent } from '../../../components/result-table/result-table.component';
import { ParamHelper } from '../helper/param-helper';
import { MatDialog } from '@angular/material';
import { ParamModalComponent } from '../param-modal/param-modal.component';
import { TeamService } from 'src/app/service/team.service';
import { Team } from 'src/app/model/team';
import { ChartType } from 'src/app/model/chart-type';
import { QueryEditorComponent } from 'src/app/components/query-editor/query-editor.component';

@Component({
  selector: 'app-ad-hoc',
  templateUrl: './ad-hoc.component.html',
  styleUrls: ['./ad-hoc.component.css']
})
export class AdHocComponent implements OnInit {

  @ViewChild(ResultTableComponent) resultTable: ResultTableComponent;
  @ViewChild(QueryEditorComponent) queryEditor: QueryEditorComponent;

  public queryRequest = new PagedQueryRequest();

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
  }
}
