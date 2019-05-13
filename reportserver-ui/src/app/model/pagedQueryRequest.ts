export class PagedQueryRequest {
    queryString: string;
    connectionUuid: string;
    itemsPerPage: number;
    neededPage: number;
    parameters: object;
    arrayParameters: Array<Parameter>;
    queryName: string;
}

export class Parameter {
    parameterName: string;
    parameterValue: any;
}

export class QueryRequests {
    queries: Array<PagedQueryRequest>;
}