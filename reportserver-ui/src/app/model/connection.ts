import { DbType } from './driver';

export class Connection {
    uuid: string;
    driverUuid: string;
    host: string;
    port: string;
    dbName: string;
    username: string;
    password: string;
    alive: boolean;
    driverType: DbType;
}

export class Connections {
    connections: Connection[];
}