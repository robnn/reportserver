export class PagedQueryRequest {
    queryString: string;
    connectionUuid: string;
    itemsPerPage: number;
    neededPage: number;
    parameters: object;
    arrayParameters: Array<Parameter>;
    queryName: string;
}

export class NotPagedQueryRequest {
    queryString: string;
    connectionUuid: string;
    parameters: object;
}

export class Parameter {
    parameterName: string;
    parameterValue: any;

    static of(key: string): Parameter {
        const param = new Parameter();
        param.parameterName = key;
        param.parameterValue = '';
        return param;
    }
}

export class QueryRequests {
    queries: Array<PagedQueryRequest>;
}