export class Team {
    uuid: string;
    name: string;
    userNames: Array<string>;
}

export class TeamResponse {
    teams: Array<Team>;
}