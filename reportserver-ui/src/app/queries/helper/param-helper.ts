export class ParamHelper {
    public static isParametrized(query: string): boolean {
        const params = this.extractParams(query)
        return params && params.length != 0;
    }

    public static extractParams(query: string): Array<string> {
        const regexp = new RegExp(':[a-zA-Z0-9]+');
        return query.match(/:[a-zA-Z0-9]+/g);
    }
}
