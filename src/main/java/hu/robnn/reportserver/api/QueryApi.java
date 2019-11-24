package hu.robnn.reportserver.api;

import hu.robnn.auth.annotation.Authenticated;
import hu.robnn.reportserver.model.dto.*;
import hu.robnn.reportserver.service.QueryManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping(path = "queries")
@CrossOrigin
public class QueryApi {

    private final QueryManager queryManager;

    public QueryApi(QueryManager queryManager) {
        this.queryManager = queryManager;
    }


    @RequestMapping(path = "/paged", method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<PagedQueryResponse> executePagedQuery(@RequestBody ParametrizedQueryRequest parametrizedQueryRequest) {
        PagedQueryResponse pagedQueryResponse = queryManager.executePaginatedQuery(parametrizedQueryRequest);
        return new ResponseEntity<>(pagedQueryResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/execution", method = RequestMethod.POST)
    @Authenticated
    public ResponseEntity<PagedQueryResponse> getExecution(@RequestBody ExecutionQueryRequest executionQueryRequest) {
        PagedQueryResponse pagedQueryResponse = queryManager.getExecutionData(executionQueryRequest);
        return new ResponseEntity<>(pagedQueryResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    @Authenticated
    public ResponseEntity<QueryRequests> listQueries(@RequestParam Integer page, @RequestParam Integer itemsPerPage) {
        return new ResponseEntity<>(queryManager.listQueries(page, itemsPerPage), HttpStatus.OK);
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @Authenticated
    public ResponseEntity<QueryRequests> listAllQueries() {
        return new ResponseEntity<>(queryManager.listAllQueries(), HttpStatus.OK);
    }

    @RequestMapping(path = "/notPaged", method = RequestMethod.POST)
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
