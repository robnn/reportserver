import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { TeamService } from 'src/app/team.service';
import { Team } from 'src/app/model/team';
import { User } from 'src/app/model/user';
import { NotificationService } from 'src/app/notification.service';

@Component({
  selector: 'app-edit-team',
  templateUrl: './edit-team.component.html',
  styleUrls: ['./edit-team.component.css']
})
export class EditTeamComponent implements OnInit {

  public users: Array<string>;
  public team: Team = new Team();

  constructor(
    public dialogRef: MatDialogRef<EditTeamComponent>,
    public teamService: TeamService,
    public notificationService: NotificationService) { }

  onNoClick(): void {
    this.dialogRef.close();
  }

  createClick(): void {
    this.teamService.manageTeam(this.team).subscribe(data => {
      this.dialogRef.close("SAVED");
    }, error => {
      this.notificationService.addNotification(error.error);
    });
  }


  ngOnInit() {
    this.teamService.listUsers().subscribe(resp => {
      this.users = resp;
      console.log(this.users);
    }, err => this.notificationService.addNotification(err.error));
  }

}
