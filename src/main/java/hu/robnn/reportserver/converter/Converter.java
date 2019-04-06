package hu.robnn.reportserver.converter;

import hu.robnn.reportserver.model.dto.PagedQueryRequest;
import hu.robnn.reportserver.model.dto.PagedQueryResponse;
import hu.robnn.reportserver.model.dto.QueryResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Converter {
    public static QueryResponse convertToQueryResult(ResultSet resultSet) throws SQLException {
        QueryResponse queryResult = new QueryResponse();
        while (resultSet.next()) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            Map<String, Object> objectMap = new HashMap<>();
            for (int i = 0; i < columnCount; i++) {
                objectMap.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
            }
            queryResult.getResult().add(objectMap);
        }
        return queryResult;
    }

    public static PagedQueryResponse convertToPagedQueryResult(ResultSet resultSet, PagedQueryRequest pagedQueryRequest) throws SQLException {
        PagedQueryResponse pagedQueryResponse = new PagedQueryResponse();
        int rowCounter = 0;
        int pageCounter = 1;
        while (resultSet.next()) {
            int columnCount = resultSet.getMetaData().getColumnCount();
            Map<String, Object> objectMap = new HashMap<>();
            for (int i = 0; i < columnCount; i++) {
                objectMap.put(resultSet.getMetaData().getColumnLabel(i + 1)
                        .toLowerCase(), resultSet.getObject(i + 1));
            }
            if (pagedQueryRequest.getNeededPage() != null && pageCounter == pagedQueryRequest.getNeededPage()) {
                pagedQueryResponse.getPagedResult().add(objectMap);
            }
            rowCounter++;
            if (pagedQueryRequest.getItemsPerPage() != null && rowCounter == pagedQueryRequest.getItemsPerPage()) {
                pageCounter ++;
                rowCounter = 0;
            }
        }
        pagedQueryResponse.setActualPage(pagedQueryRequest.getNeededPage());
        pagedQueryResponse.setItemsPerPage(pagedQueryRequest.getItemsPerPage());
        pagedQueryResponse.setTotalNumberOfPages(pageCounter - 1);
        return pagedQueryResponse;
    }
}