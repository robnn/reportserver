import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../environments/environment'
import { tap } from 'rxjs/operators';
import { NotificationService } from './service/notification.service';
import { Message } from './model/message';
import { Severity } from './model/severity';

@Injectable()
export class ApiInterceptor implements HttpInterceptor {
    constructor(private notificationService: NotificationService) {}

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const baseUrl = environment.baserUrl;
        if (req.url.includes('drivers') || req.url.includes('users') || req.url.includes('connections') 
        || req.url.includes('queries') || req.url.includes('teams') || req.url.includes('dashboards')) {
            const apiReq = req.clone({ url: `${baseUrl}${req.url}` });
            return next.handle(apiReq)
            // .pipe(tap(evt => {
            //     if (evt instanceof HttpResponse) {
            //         if(evt.body && evt.body.success) {
            //             this.notificationService.addNotification(new Message(Severity.INFO, "SUCCESS"));
            //         }
            //     }
            // }));
        } else {
            return next.handle(req);
        }
    }
}