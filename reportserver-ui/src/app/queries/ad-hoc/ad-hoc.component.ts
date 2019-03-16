import { Component, OnInit } from '@angular/core';
import { ConnectionService } from 'src/app/connection.service';
import { Connections } from 'src/app/model/connection';
import { NotificationService } from 'src/app/notification.service';

@Component({
  selector: 'app-ad-hoc',
  templateUrl: './ad-hoc.component.html',
  styleUrls: ['./ad-hoc.component.css']
})
export class AdHocComponent implements OnInit {

  public connections: Connections;
  public connectionUuid: string;
  public query: string;

  constructor(private connectionService: ConnectionService,
    private notificationService: NotificationService) { }

  ngOnInit() {
    this.connectionService.listConnections().subscribe(resp => {
      this.connections = resp;
    }, err => this.notificationService.addNotification(err.error));
  }

  executeQuery() {
    this.connectionService.runQuery(this.connectionUuid, this.query).subscribe(resp => {
      console.log(resp);
    });
  }
}
