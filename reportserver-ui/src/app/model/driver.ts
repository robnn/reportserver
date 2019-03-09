export enum DbType {
    ORACLE_THIN = "ORACLE_THIN",
    MY_SQL = "MY_SQL",
    MICROSOFT_SQL_SERVER = "MICROSOFT_SQL_SERVER",
    POSTGRESQL = "POSTGRESQL",
    DB2 = "DB2",
    SQLITE = "SQLITE",
}

export class Driver {
    uuid: string;
    name: string;
    driverClassName: string;
    dbType: DbType;
}

export class Drivers {
    drivers: Driver[];
}