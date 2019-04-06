package hu.robnn.reportserver.api;

import hu.robnn.auth.annotation.Authenticated;
import hu.robnn.reportserver.model.dto.Driver;
import hu.robnn.reportserver.model.dto.Drivers;
import hu.robnn.reportserver.service.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Component
@RestController
@RequestMapping(path = "drivers")
@CrossOrigin
public class DriverApi {

    private final DriverService driverService;

    public DriverApi(DriverService driverService) {
        this.driverService = driverService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<Driver> installDriver(@RequestParam MultipartFile multipartFile, @RequestParam String dbType){
        return new ResponseEntity<>(driverService.installDriver(multipartFile, dbType), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Authenticated
    public ResponseEntity<Drivers> listDrivers(){
        return new ResponseEntity<>(driverService.listInstalledDrivers(), HttpStatus.OK);
    }
}
