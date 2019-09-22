package hu.robnn.reportserver.converter;

import hu.robnn.reportserver.model.dmo.query.HQueryColumn;
import hu.robnn.reportserver.model.dto.Column;
import hu.robnn.reportserver.model.dto.PagedQueryRequest;
import hu.robnn.reportserver.model.dto.PagedQueryResponse;
import hu.robnn.reportserver.model.dto.QueryResponse;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Converter {
    public static QueryResponse convertToQueryResult(ResultSet resultSet) throws SQLException {
        QueryResponse queryResult = new QueryResponse();
        while (resultSet.next()) {
            Map<String, Object> objectMap = initializeResultMap(resultSet);
            queryResult.getResult().add(objectMap);
        }
        return queryResult;
    }

    public static PagedQueryResponse convertToPagedQueryResult(ResultSet resultSet, PagedQueryRequest pagedQueryRequest, List<Column> columns) throws SQLException {
        PagedQueryResponse pagedQueryResponse = new PagedQueryResponse();
        int rowCounter = 0;
        int pageCounter = 1;
        int itemCounter = 0;
        while (resultSet.next()) {
            Map<String, Object> objectMap = initializeResultMap(resultSet);;
            itemCounter ++;
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
        pagedQueryResponse.setTotalItems(itemCounter);
        pagedQueryResponse.setColumns(columns);
        pagedQueryResponse.setVisibility(pagedQueryRequest.getVisibility());
        return pagedQueryResponse;
    }

    private static Map<String, Object> initializeResultMap(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        Map<String, Object> objectMap = new HashMap<>();
        for (int i = 0; i < columnCount; i++) {
            objectMap.put(resultSet.getMetaData().getColumnLabel(i + 1)
                    .toLowerCase(), resultSet.getObject(i + 1));
        }
        return objectMap;
    }

    public static Set<HQueryColumn> getColumns(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        Set<HQueryColumn> target = new HashSet<>();
        for (int i = 0; i < columnCount; i++) {
            HQueryColumn it = new HQueryColumn();
            it.setColumnName(resultSet.getMetaData().getColumnLabel(i + 1));
            it.setColumnType(resultSet.getMetaData().getColumnTypeName(i + 1));
            target.add(it);
        }
        return target;
    }
}