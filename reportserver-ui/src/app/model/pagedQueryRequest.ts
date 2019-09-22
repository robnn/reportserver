export class PagedQueryRequest {
    queryString: string;
    connectionUuid: string;
    itemsPerPage: number;
    neededPage: number;
    parameters: object;
    arrayParameters: Array<Parameter>;
    queryName: string;
    visibility: QueryVisibility;
    teamUuidsAndNames: Array<TeamUuidAndName>;
}

export class NotPagedQueryRequest {
    queryString: string;
    connectionUuid: string;
    parameters: object;
}

export class TeamUuidAndName {
    constructor(uuid: string,
        name:string) { }
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

export enum QueryVisibility {
    PUBLIC = "PUBLIC",
    PRIVATE = "PRIVATE",
    TEAM = "TEAM",
}