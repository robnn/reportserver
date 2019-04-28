export class PagedQueryRequest {
    queryString: string;
    connectionUuid: string;
    itemsPerPage: number;
    neededPage: number;
    parameters: object;
    queryName: string;
}

export class QueryRequests {
    queries: Array<PagedQueryRequest>;
}