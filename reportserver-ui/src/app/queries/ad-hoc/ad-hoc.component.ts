import { Component, OnInit } from '@angular/core';
import { ConnectionService } from 'src/app/connection.service';
import { Connections } from 'src/app/model/connection';
import { NotificationService } from 'src/app/notification.service';
import { QueryService } from 'src/app/query.service';
import { PagedQueryRequest } from 'src/app/model/pagedQueryRequest';

@Component({
  selector: 'app-ad-hoc',
  templateUrl: './ad-hoc.component.html',
  styleUrls: ['./ad-hoc.component.css']
})
export class AdHocComponent implements OnInit {

  public connections: Connections;
  public connectionUuid: string;
  public query: string;
  public queryResult: Array<Map<string, object>>;
  public objectValues = Object.values;
  public keys;

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

  executePagedQuery(neededPage: number) {
    const pagedQueryRequest = new PagedQueryRequest();
    pagedQueryRequest.connectionUuid = this.connectionUuid;
    pagedQueryRequest.queryString = this.query;
    pagedQueryRequest.itemsPerPage = 5;
    pagedQueryRequest.neededPage = 1;
    this.queryService.runPagedQuery(pagedQueryRequest).subscribe(resp => {
      this.queryResult = resp.pagedResult;
      this.keys = Object.keys(this.queryResult[0]);
      console.log(this.keys);
    }, err => {
      this.notificationService.addNotification(err.err);
    })
  }
}
