import { Component, OnInit } from '@angular/core';
import { QueryService } from 'src/app/query.service';
import { ConnectionService } from 'src/app/connection.service';
import { NotificationService } from 'src/app/notification.service';
import { Connections } from 'src/app/model/connection';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-saved-queries',
  templateUrl: './saved-queries.component.html',
  styleUrls: ['./saved-queries.component.css']
})
export class SavedQueriesComponent implements OnInit {

  public queries;
  public connections: Connections;

  constructor(private queryService: QueryService,
    private connectionService: ConnectionService,
    private notificationService: NotificationService,
    private translateService: TranslateService) { }

  ngOnInit() {
    this.connectionService.listConnections().subscribe(resp => {
      this.connections = resp;
    }, err => this.notificationService.addNotification(err.error));
    this.queryService.listSavedQueries().subscribe(resp => {
      this.queries = resp;
    })
  }

  getConnectionInfoForUuid(connectionUuid): string {
    if (this.connections) {
      const connection = this.connections.connections.find(connection => connection.uuid == connectionUuid);
      return `${this.translateService.instant(connection.driverType)} @ ${connection.host}:${connection.port} - ${connection.dbName}`;
    }
    return '';
  }

}
