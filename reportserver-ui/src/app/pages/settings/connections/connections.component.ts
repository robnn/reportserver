import { Component, OnInit } from '@angular/core';
import { ConnectionService } from 'src/app/service/connection.service';
import { Connections } from 'src/app/model/connection';
import { AddConnectionDialogComponent } from './add-connection-dialog/add-connection-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-connections',
  templateUrl: './connections.component.html',
  styleUrls: ['./connections.component.css']
})
export class ConnectionsComponent implements OnInit {

  public connections: Connections;

  constructor(private connectionService: ConnectionService,
    public dialog: MatDialog) { }

  ngOnInit() {
    this.connectionService.listConnections().subscribe(resp => {
      this.connections = resp;
    })
  }
  

  openDialog(): void {
    const dialogRef = this.dialog.open(AddConnectionDialogComponent, {
      width: '400px'
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === "SAVED"){
        this.ngOnInit();
      }
    });
  }
}
