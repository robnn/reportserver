import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Message } from './model/message';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private notifications = new Subject<Message[]>();

  constructor(private router: Router) { }

  public addNotification = (notification: Message): void => {
    if (notification.message == "INVALID_TOKEN") {
        this.router.navigate(['/login']);
    }
    this.notifications.next(new Array(notification));
  }

  public getNotifications = () =>
      this.notifications.asObservable()

}
