package hu.robnn.reportserver.api;

import hu.robnn.auth.annotation.Authenticated;
import hu.robnn.reportserver.model.dto.Dashboard;
import hu.robnn.reportserver.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping(path = "dashboards")
@CrossOrigin
public class DashboardApi {

    private final DashboardService dashboardService;

    public DashboardApi(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<Dashboard> manageDashboard(@RequestBody Dashboard dashboard){
        return new ResponseEntity<>(dashboardService.manageDashboard(dashboard), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Authenticated
    public ResponseEntity<Dashboard> getDashboardForUser(@RequestParam String username) {
        return new ResponseEntity<>(dashboardService.findDashboardByUsername(username), HttpStatus.OK);
    }
}
