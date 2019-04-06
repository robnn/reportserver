export class User {
    uuid: string;
    username: string;
    emailAddress: string;
    roles: Role[];
    password: string;
}

export class Role {
    roleCode: String;
}
