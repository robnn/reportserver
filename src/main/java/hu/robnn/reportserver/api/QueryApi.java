package hu.robnn.reportserver.api;

import hu.robnn.auth.annotation.Authenticated;
import hu.robnn.reportserver.converter.Converter;
import hu.robnn.reportserver.model.dto.*;
import hu.robnn.reportserver.service.QueryManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;
import java.util.UUID;

@Component
@RestController("drivers")
@RequestMapping(path = "queries")
@CrossOrigin
public class QueryApi {

    private final QueryManager queryManager;

    public QueryApi(QueryManager queryManager) {
        this.queryManager = queryManager;
    }

    @RequestMapping(path = "/simple", method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<QueryResponse> executeSimpleQuery(@RequestParam String connectionUuid, @RequestParam String query) throws Exception {
        ResultSet resultSet = queryManager.executeQuery(UUID.fromString(connectionUuid), query);
        QueryResponse queryResponse = Converter.convertToQueryResult(resultSet);
        return new ResponseEntity<>(queryResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/paged", method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<PagedQueryResponse> executePagedQuery(@RequestBody ParametrizedQueryRequest parametrizedQueryRequest) {
        PagedQueryResponse pagedQueryResponse = queryManager.executePaginatedQuery(parametrizedQueryRequest);
        return new ResponseEntity<>(pagedQueryResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Authenticated
    public ResponseEntity<QueryRequests> listQueries() {
        return new ResponseEntity<>(queryManager.listQueries(), HttpStatus.OK);
    }

    @RequestMapping(path = "/parametrized", method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<QueryResponse> executeNotPagedQuery(@RequestBody NotPagedParametrizedQueryRequest notPagedParametrizedQueryRequest) {
        QueryResponse queryResponse = queryManager.executeQuery(notPagedParametrizedQueryRequest);
        return new ResponseEntity<>(queryResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/getColumns", method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<List<Column>> listColumns(@RequestBody ParametrizedQueryRequest parametrizedQueryRequest) {
        List<Column> queryResponse = queryManager.getQueryColumns(parametrizedQueryRequest);
        return new ResponseEntity<>(queryResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/save", method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<ParametrizedQueryRequest> saveQuery(@RequestBody ParametrizedQueryRequest parametrizedQueryRequest) {
        ParametrizedQueryRequest pagedQueryResponse = queryManager.saveQuery(parametrizedQueryRequest);
        return new ResponseEntity<>(pagedQueryResponse, HttpStatus.OK);
    }


}
