import { Severity } from './severity';

export class Message {
    severity: Severity;
    message: string;
    constructor(severity: Severity, message: string){
        this.severity = severity;
        this.message = message;
    }
}
