import { Component, OnInit, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { TeamService } from 'src/app/service/team.service';
import { Team } from 'src/app/model/team';
import { NotificationService } from 'src/app/service/notification.service';

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
    public notificationService: NotificationService,
    @Inject(MAT_DIALOG_DATA) public inputTeam: Team) {
      if (inputTeam) {
        this.team = inputTeam
      }
     }

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
    }, err => this.notificationService.addNotification(err.error));
  }

}
