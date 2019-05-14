export class QueryResponse {
    result: Array<Map<string, object>>;
}

export class PagedQueryResponse {
    pagedResult: Array<Map<string, object>>;
    totalNumberOfPages: number;
    actualPage: number;
    itemsPerPage: number;
    totalItems: number;
}