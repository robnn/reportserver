package hu.robnn.reportserver.api;

import hu.robnn.auth.annotation.Authenticated;
import hu.robnn.auth.enums.UserRole;
import hu.robnn.reportserver.model.dto.Driver;
import hu.robnn.reportserver.model.dto.Drivers;
import hu.robnn.reportserver.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Connection;

@Component
@RestController("/drivers")
@CrossOrigin
public class DriverApi {

    private final DriverService driverService;

    public DriverApi(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping(path = "drivers", method = RequestMethod.POST)
//    @Authenticated(neededRole = UserRole.USER)
    public ResponseEntity<Driver> installDriver(@RequestParam MultipartFile multipartFile){
        return new ResponseEntity<>(driverService.installDriver(multipartFile), HttpStatus.CREATED);
    }

    @RequestMapping(path = "drivers", method = RequestMethod.GET)
//    @Authenticated(neededRole = UserRole.USER)
    public ResponseEntity<Drivers> listDrivers(){
        return new ResponseEntity<>(driverService.listInstalledDrivers(), HttpStatus.OK);
    }

    @RequestMapping(path = "drivers/connection", method = RequestMethod.GET)
//    @Authenticated(neededRole = UserRole.USER)
    public ResponseEntity<Connection> getConnection(){
        Driver driver = new Driver();
        driver.setUuid("a59b4e5a-e24f-4629-a9f3-830122a90e2a");
        return new ResponseEntity<>(driverService.getConnectionForDriver(driver, "alma"), HttpStatus.OK);
    }
}
