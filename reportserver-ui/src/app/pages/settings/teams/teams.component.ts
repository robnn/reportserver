import { Component, OnInit } from '@angular/core';
import { TeamService } from 'src/app/service/team.service';
import { Team } from 'src/app/model/team';
import { MatDialog } from '@angular/material';
import { EditTeamComponent } from './edit-team/edit-team.component';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent implements OnInit {

  public teams: Array<Team>;

  constructor(private teamService: TeamService,
              public dialog: MatDialog) { }

  ngOnInit() {
    this.teamService.listTeams().subscribe(resp => {
      this.teams = resp.teams;
    });
  }

  openDialog(team: Team): void {
    if (!team) {
      team = new Team()
    }
    const dialogRef = this.dialog.open(EditTeamComponent, {
      width: '400px',
      data: team,
    });

    dialogRef.afterClosed().subscribe(result => {
      if(result === "SAVED"){
        this.ngOnInit();
      }
    });
  }

  openForEditing(team: Team) {
    this.openDialog(team);
  }

}
