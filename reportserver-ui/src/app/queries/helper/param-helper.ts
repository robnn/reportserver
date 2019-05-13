import { Parameter } from 'src/app/model/pagedQueryRequest';

export class ParamHelper {
    public static isParametrized(query: string): boolean {
        const params = this.extractParams(query);
        return params && params.length !== 0;
    }

    public static extractParams(query: string): Array<string> {
        const regexp = new RegExp(':[a-zA-Z0-9]+');
        return query.match(/:[a-zA-Z0-9]+/g);
    }

    public static mapToArray(object: object): Array<Parameter> {
        const keys = Object.keys(object);
        const values = Object.values(object);
        const extracted = new Array<Parameter>();
        for (let index = 0; index < keys.length; index++) {
            const key = keys[index];
            const value = values[index];
            const param = new Parameter();
            param.parameterName = key;
            param.parameterValue = value;
            extracted.push(param);
        }
        return extracted;
    }
}
