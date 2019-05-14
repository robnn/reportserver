import { PagedQueryRequest, NotPagedQueryRequest } from 'src/app/model/pagedQueryRequest';

export class RequestHelper {
    public static convertToNotPagedRequest(paged: PagedQueryRequest): NotPagedQueryRequest {
        const request = new NotPagedQueryRequest();
        request.connectionUuid = paged.connectionUuid;
        request.parameters = paged.parameters;
        request.queryString = paged.queryString;
        return request;
    }
}
