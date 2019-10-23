export class PagedQueryRequest {
    uuid: string;
    queryString: string = "";
    connectionUuid: string;
    itemsPerPage: number = 10;
    neededPage: number = 1;
    parameters: object;
    arrayParameters: Array<Parameter>;
    queryName: string = "";
    visibility: QueryVisibility;
    teamUuidsAndNames: Array<TeamUuidAndName>;
    charts = new Array<ChartDiagram>();

    setAllFrom(other: PagedQueryRequest) {
        this.queryString = other.queryString;
        this.connectionUuid = other.connectionUuid;
        this.itemsPerPage = other.itemsPerPage;
        this.neededPage = other.neededPage;
        this.parameters = other.parameters;
        this.arrayParameters = other.arrayParameters;
        this.queryName = other.queryName;
        this.visibility = other.visibility;
        this.teamUuidsAndNames = other.teamUuidsAndNames;
        this.charts = other.charts;
    }
}

export class NotPagedQueryRequest {
    queryString: string;
    connectionUuid: string;
    parameters: object;
}

export class Column {
    columnName: string;
    columnType: string; 
}

export class ChartDiagram {
    chartType: string;
    labelColumn: string;
    dataColumn: string;
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

export class PagedQueryRequests {
    actualPage: number;
    itemsPerPage: number;
    totalItems: number;
    queries: Array<PagedQueryRequest>;
}

export class QueryRequests {
    queries: Array<PagedQueryRequest>;
}

export enum QueryVisibility {
    PUBLIC = "PUBLIC",
    PRIVATE = "PRIVATE",
    TEAM = "TEAM",
}