package hu.robnn.reportserver.api;

import hu.robnn.auth.annotation.Authenticated;
import hu.robnn.reportserver.model.dto.ConnectionDescriptor;
import hu.robnn.reportserver.model.dto.ConnectionDescriptorsResponse;
import hu.robnn.reportserver.service.ConnectionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@RestController
@RequestMapping(path = "connections")
@CrossOrigin
public class ConnectionApi {

    private final ConnectionManager connectionManager;

    public ConnectionApi(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Authenticated(acceptedRoles = {"ADMIN"})
    public ResponseEntity<ConnectionDescriptor> createConnection(@RequestBody ConnectionDescriptor connectionDescriptor){
        return new ResponseEntity<>(connectionManager.createConnection(connectionDescriptor), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Authenticated
    public ResponseEntity<ConnectionDescriptorsResponse> listConnections(){
        return new ResponseEntity<>(new ConnectionDescriptorsResponse(connectionManager.listConnections()), HttpStatus.OK);
    }
}
