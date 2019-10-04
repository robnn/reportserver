import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserService } from './user.service';
import { Observable } from 'rxjs';
import { TeamResponse, Team } from '../model/team';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private connectionUrl = '/teams/';

  constructor(private http: HttpClient,
    private userService: UserService) { }

    listTeams() : Observable<TeamResponse> {
      return this.http.get<TeamResponse>(this.connectionUrl, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
    }

    manageTeam(team: Team) {
      return this.http.post(this.connectionUrl, team, { headers: this.userService.getAuthTokenAsHttpHeader(null) });
    }

    listUsers() {
      return this.http.get<Array<string>>(this.connectionUrl + "listUsernames", { headers: this.userService.getAuthTokenAsHttpHeader(null) });
    }
}
