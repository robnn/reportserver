package hu.robnn.reportserver.api;

import hu.robnn.auth.annotation.Authenticated;
import hu.robnn.auth.dao.UserDao;
import hu.robnn.reportserver.model.dto.ConnectionDescriptor;
import hu.robnn.reportserver.model.dto.Team;
import hu.robnn.reportserver.model.dto.TeamsResponse;
import hu.robnn.reportserver.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping(path = "teams")
@CrossOrigin
public class TeamApi {
    private final TeamService teamService;

    public TeamApi(TeamService teamService) {
        this.teamService = teamService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Authenticated(acceptedRoles = {"ADMIN"})
    public ResponseEntity<Team> createTeam(@RequestBody Team team){
        return new ResponseEntity<>(teamService.manageTeam(team), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Authenticated
    public ResponseEntity<TeamsResponse> listTeams() {
        return new ResponseEntity<>(teamService.listTeams(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/listUsernames")
    @Authenticated(acceptedRoles = {"ADMIN"})
    public ResponseEntity<List<String>> listUsernames() {
        return new ResponseEntity<>(teamService.listUsernames(), HttpStatus.OK);
    }
}
