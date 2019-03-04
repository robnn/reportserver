package hu.robnn.reportserver.api;

import hu.robnn.reportserver.model.dto.ConnectionDescriptor;
import hu.robnn.reportserver.model.dto.ConnectionDescriptorsResponse;
import hu.robnn.reportserver.service.ConnectionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Component
@RestController("/connections")
@CrossOrigin
public class ConnectionApi {

    private final ConnectionManager connectionManager;

    public ConnectionApi(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @RequestMapping(path = "connections", method = RequestMethod.POST)
//    @Authenticated(neededRole = UserRole.USER)
    public ResponseEntity<ConnectionDescriptor> createConnection(@RequestBody ConnectionDescriptor connectionDescriptor){
        return new ResponseEntity<>(connectionManager.createConnection(connectionDescriptor), HttpStatus.CREATED);
    }

    @RequestMapping(path = "connections", method = RequestMethod.GET)
    //    @Authenticated(neededRole = UserRole.USER)
    public ResponseEntity<ConnectionDescriptorsResponse> listConnections(){
        return new ResponseEntity<>(new ConnectionDescriptorsResponse(connectionManager.listConnections()), HttpStatus.OK);
    }

    @RequestMapping(path = "connections/query", method = RequestMethod.POST)
    //    @Authenticated(neededRole = UserRole.USER)
    public ResponseEntity<Integer> listConnections(@RequestParam String connectionUuid, @RequestParam String query){
        connectionManager.executeQuery(UUID.fromString(connectionUuid), query);
        return new ResponseEntity<>(5, HttpStatus.HTTP_VERSION_NOT_SUPPORTED);
    }
}
